/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/9 上午8:25
 */
package com.spring.dependency.lookup;

import com.spring.domain.SuperUser;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次依赖查找Demo
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {

        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 获取层次BeanFactory HierarchicalBeanFactory < ConfigurableBeanFactory < ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        //System.out.println("当前BeanFactory：" + beanFactory + " 父类BeanFactory：" + beanFactory.getParentBeanFactory());

        ConfigurableListableBeanFactory parentBeanFactory = createParentBeanFactory();
        // 设置Parent BeanFactory
        beanFactory.setParentBeanFactory(parentBeanFactory);

        //System.out.println("当前BeanFactory：" + beanFactory + " 父类BeanFactory：" + beanFactory.getParentBeanFactory());

        // 子BeanFactory不包含父BeanFactory的Bean
        // displayContainsLocalBean(beanFactory, "user");
        // displayContainsLocalBean(parentBeanFactory, "user");

        /**
         * 双亲委派"方式查询子BeanFactory没有则查询父BeanFactory（HierarchicalBeanFactory），
         * 因为普通的HierarchicalBeanFactory判断是否包含某个Bean时，只会查自己的不会去父类查询
         *
         * BeanFactoryUtils 工具类中提供了一个方法，beansOfTypeIncludingAncestors，支持"双亲委派"查询
          */

        // displayContainsBean(beanFactory, "user");
        // displayContainsBean(parentBeanFactory, "user");

        /**
         * BeanFactoryUtils 工具类中提供了一个方法，beansOfTypeIncludingAncestors，支持"双亲委派"查询
         */
        displayContainsBeanByBeanFactoryUtils(beanFactory, SuperUser.class);
        displayContainsBeanByBeanFactoryUtils(parentBeanFactory, SuperUser.class);


        applicationContext.refresh();

        applicationContext.close();
    }

    /**
     * BeanFactoryUtils 工具类中提供了一个方法，beansOfTypeIncludingAncestors，支持"双亲委派"查询
     * 本BeanFactory没有Bean，则找parent BeanFactory，直到找到或者没有parent BeanFactory
     *
     * @param beanFactory
     * @param clazz
     */
    private static void displayContainsBeanByBeanFactoryUtils(ConfigurableListableBeanFactory beanFactory, Class<SuperUser> clazz) {

        // 注意，该方法如果同一类型的Bean存在多个，会抛异常 NoUniqueBeanDefinitionException
        SuperUser superUser = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, clazz);
        System.out.printf("当前BeanFactory[%s]是否包含BeanName[%s] : %s \n", beanFactory, clazz.getName(), superUser != null);
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前BeanFactory[%s]是否包含BeanName[%s] : %s \n", beanFactory, beanName, containsBean(beanFactory, beanName));
    }

    /**
     * 递归查询bean是否包含于BeanFactory或ParentBeanFactory
     *
     * @param beanFactory
     * @param beanName
     * @return
     */
    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {

        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);

    }

    public static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前BeanFactory[%s]是否包含Local BeanName[%s] : %s \n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
    }

    /**
     * 创建父类BeanFactory
     *
     * @return
     */
    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(location);

        return beanFactory;
    }
}
