/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/22 下午10:16
 */
package com.spring.dependency.lookup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * Spring 中常见异常
 *       NoSuchBeanDefinitionException             当查找 Bean 不存在于 IoC 容器时            BeanFactory#getBean ObjectFactory#getObject
 *
 *       NoUniqueBeanDefinitionException           类型依赖查找时，IoC 容器存在多个Bean实例     BeanFactory#getBean(Class)
 *
 *       BeanInstantiationException                当Bean所对应的类型非具体类时                BeanFactory#getBean
 *
 *       BeanCreationException                     当Bean初始化过程中                        Bean初始化方法执行异常时
 *
 *       BeanDefinitionStoreException              当BeanDefinition配置元信息非法时           XML 配置资源无法打开时
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class LockupExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 该类作为配置类
        applicationContext.register(LockupExceptionDemo.class);

        //applicationContext.refresh();

        // NoSuchBeanDefinitionException 最常见的异常，没有该Bean
        // Object user = applicationContext.getBean("user");

        // NoUniqueBeanDefinitionException，查找对应类型的Bean多于一个
        // String bean = applicationContext.getBean(String.class);

        // BeanInstantiationException，CharSequence是一个接口，不能初始化
       /* BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);
        applicationContext.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());
        applicationContext.refresh();*/

        // BeanCreationException，初始化的过程中出错了
        // Object bean = applicationContext.getBean("bean3");

        // 注意必须是在容器启动前
        // BeanCreationException
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Pojo.class);
        applicationContext.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());
        applicationContext.refresh();

        applicationContext.close();

    }

    static class Pojo implements InitializingBean {

        @PostConstruct // CommonAnnotationBeanPostProcessor 处理的，优先于实现InitializingBean
        public void init() {
            throw new RuntimeException("init error");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new RuntimeException("afterPropertiesSet error");
        }
    }

    //@Bean
    public String bean1(){
        return "bean1";
    }

    //@Bean
    public String bean2(){
        return "bean2";
    }

    // @Bean
    public String bean3(){
        throw new RuntimeException("错了错了");
    }

}
