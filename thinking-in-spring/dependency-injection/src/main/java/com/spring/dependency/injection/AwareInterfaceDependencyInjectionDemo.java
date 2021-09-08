/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/24 下午6:57
 */
package com.spring.dependency.injection;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 接口回调注入 {@link org.springframework.beans.factory.Aware}
 * 內建接口                                说明
 *             BeanFactoryAware                    获取 IoC 容器 - BeanFactory
 *             ApplicationContextAware             获取 Spring 应用上下文 - ApplicationContext 对象
 *             EnvironmentAware                    获取 Environment 对象
 *             ResourceLoaderAware                 获取资源加载器 对象 - ResourceLoader
 *             BeanClassLoaderAware                获取加载当前 Bean Class 的 ClassLoader
 *             BeanNameAware                       获取当前 Bean 的名称
 *             MessageSourceAware                  获取 MessageSource 对象，用于 Spring 国际化
 *             ApplicationEventPublisherAware      获取 ApplicationEventPublishAware 对象，用于 Spring 事件
 *             EmbeddedValueResolverAware          获取 StringValueResolver 对象，用于占位符处理
 *
 * 注意这些Aware 接口都是Spring内置的，不支持自己实现
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class AwareInterfaceDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware {

    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AwareInterfaceDependencyInjectionDemo.class);

        applicationContext.refresh();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        AwareInterfaceDependencyInjectionDemo bean = applicationContext.getBean(AwareInterfaceDependencyInjectionDemo.class);

        System.out.println(beanFactory == bean.beanFactory);
        System.out.println(applicationContext == bean.applicationContext);

        applicationContext.close();

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
