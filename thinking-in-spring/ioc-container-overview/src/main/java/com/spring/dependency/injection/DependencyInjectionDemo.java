/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/8/23 下午5:34
 */
package com.spring.dependency.injection;

import com.spring.annotation.Super;
import com.spring.domain.User;
import com.spring.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Spring Ioc 依赖注入Demo
 * @author HanZhonghua
 * @version 1.0
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {

        // 配置xml，启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("/META-INF/dependency-injection-context.xml");

        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        // System.out.println(userRepository.getUsers());




        //ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
       // System.out.println(objectFactory.getObject());
       // System.out.println(objectFactory.getObject() == beanFactory);

        // BeanFactory 不是一个Bean
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        whoIsIocContainer(beanFactory, userRepository);

    }

    /**
     * BeanFactory ApplicationContext 哪个才是真正的Ioc容器
     *
     * BeanFactory 是最底层的Ioc容器，ApplicationContext是BeanFactory 的一个子接口，在BeanFactory的基础上扩展支持了
     * Aop，Event，消息以及Web应用
     *
     * @param beanFactory 实际上是自定义的：ClassPathXmlApplicationContext
     * @param userRepository
     */
    private static void whoIsIocContainer (BeanFactory beanFactory, UserRepository userRepository) {

        // 依赖注入
        System.out.println(userRepository.getBeanFactory());
        /**
         * false
         * 通过 byType 注入内置的非 Bean 对象，要想知道 userRepository.getBeanFactory() 的具体类型，首先看下
         * userRepository 是如何获取的 =>
         *  beanFactory.getBean() => AbstractApplicationContext.getBean()
         *  => AbstractRefreshableApplicationContext.getBeanFactory()(DefaultListableBeanFactory).getBean()
         *  => AbstractRefreshableApplicationContext.prepareBeanFactory() => beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
         *  这里的 BeanFactory 就是 DefaultListableBeanFactory 了，所以通过byType注入BeanFactory时就是 DefaultListableBeanFactory
         *  所以真正的IOC就是BeanFactory的底层实现，ApplicationContext 通过组合了 DefaultListableBeanFactory 来实现 getBean()等方法
         *
          */
        System.out.println(beanFactory == userRepository.getBeanFactory());
    }

}
