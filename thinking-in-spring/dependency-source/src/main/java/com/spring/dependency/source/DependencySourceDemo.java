/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/4 下午5:31
 */
package com.spring.dependency.source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖来源Demo
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class DependencySourceDemo {

    // 注入在 AutowiredAnnotationBeanPostProcessor.postProcessProperties() 执行，早于 setter注入，早于@PostConstruct

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 依赖注入来源
     *
     * 上面4个类可以注入，在refresh().prepareBeanFactory() 中可以看到：
     *      beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
     * 		beanFactory.registerResolvableDependency(ResourceLoader.class, this);
     * 		beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
     * 		beanFactory.registerResolvableDependency(ApplicationContext.class, this);
     * 通过	ConfigurableListableBeanFactory.registerResolvableDependency 注入的，这些Bean不会被 Spring 托管，所以在下面的依赖查找时都找不到
     *
     * 这里可以总结依赖注入的来源有：
     * 1.Spring BeanDefinition（主要是业务 Bean）
     *      xml定义的 Bean
     *      @Bean 注解的 Bean
     *      BeanDefinitionBuilder
     * 2.单例对象
     *      registerSingleton() 创建
     * 3.非 Spring 容器管理对象（上面的几个 Bean，Spring内部的Bean ）
     *      也可以称为游离对象
     */
    @PostConstruct
    public void initByInjection() {
        System.out.println("beanFactory == applicationContext :" + (beanFactory == applicationContext));
        System.out.println("beanFactory == applicationContext.getBeanFactory :" + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
        System.out.println("resourceLoader == applicationContext :" + (resourceLoader == applicationContext));
        System.out.println("applicationEventPublisher == applicationContext :" + (applicationEventPublisher == applicationContext));
    }

    /**
     * 依赖查找来源
     *
     * 发现下面的几个 Bean 可以注入成功，但是查找时报错：NoSuchBeanDefinitionException
     * 这是因为查找的来源只限于 Spring 托管的 Bean，而下面的几个类不是 Spring 托管的，查找不了
     *
     * 查找来源：
     * 1.Spring BeanDefinition
     *      xml定义的 Bean
     *      @Bean 注解的 Bean
     *      BeanDefinitionBuilder
     * 2.单例对象
     *      registerSingleton() 创建
     */
    @PostConstruct
    public void initByLockup() {
        getBean(BeanFactory.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationEventPublisher.class);
        getBean(ApplicationContext.class);

    }


    private <T> T getBean(Class<T> classType) {

        try {
            return beanFactory.getBean(classType);
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println("当前类型:" + classType + " 无法在 BeanFactory中找到");
        }
        return null;
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(DependencySourceDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml文件，创建BeanDefinition
        reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        // 启动
        applicationContext.refresh();

        DependencySourceDemo bean = applicationContext.getBean(DependencySourceDemo.class);

        System.out.println("beanFactory:" + bean.beanFactory);

        applicationContext.close();
    }
}
