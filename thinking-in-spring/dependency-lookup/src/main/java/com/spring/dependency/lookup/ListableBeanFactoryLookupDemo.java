/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/13 下午11:32
 */
package com.spring.dependency.lookup;

import com.spring.domain.User;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * ListableBeanFactory 依赖查找Demo
 * 集合类型查找，可以查询Bean name集合，也可以查询 Bean实例集合
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class ListableBeanFactoryLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = createBeanFactory();

        // 启动Spring容器
        applicationContext.refresh();

        // 查找Bean 实例集合
        Map<String, User> beansOfType = applicationContext.getBeansOfType(User.class);
        System.out.println(beansOfType);

        // 查找Bean name集合
        String[] names = applicationContext.getBeanNamesForType(User.class);
        System.out.println(Arrays.asList(names));


        User bean = applicationContext.getBean(User.class);
        System.out.println(bean);



        applicationContext.close();
    }

    private static AnnotationConfigApplicationContext createBeanFactory() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(location);

        return beanFactory;
    }
}
