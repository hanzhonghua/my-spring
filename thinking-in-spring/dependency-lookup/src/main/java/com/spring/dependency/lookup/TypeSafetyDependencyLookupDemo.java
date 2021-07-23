/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/14 下午11:19
 */
package com.spring.dependency.lookup;

import com.spring.domain.SuperUser;
import com.spring.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 安全类型查找
 *
 *       依赖查找类型                    代表实现                                是否安全
 *       单一类型查找                    BeanFactory#getBean                     否
 *                                     ObjectFactory#getObject                 否
 *                                     ObjectProvider#getIfAvailable           是
 *
 *       集合类型查找                    ListableBeanFactory#getBeansOfType      是
 *                                     ObjectProvider#stream                   是
 *
 *       注意:层次性依赖查找的安全性取决于其扩展的单一或集合类型的 BeanFactory 接口
 *
 * 这里的安全指的是异常安全，既安全的就是不会抛出关于没有或者多个Bean的异常
 * DefaultListableBeanFactory 默认实现的BeanFactory，包含了单一、层次、类型、集合查找
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 本类作为配置类
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);

        applicationContext.refresh();

        // 展示BeanFactory.getBean()是否是安全的，不是安全的
        // displayBeanFactoryGetBean(applicationContext);

        // 展示BeanFactory.getBean()是否是安全的，不是安全的
        // displayObjectFactoryGetObject(applicationContext);

        // 展示ObjectProvider.GetIfAvailable()是否是安全的，是安全的，但是存在多个同类型时，仍然抛异常
        // displayObjectProviderGetIfAvailable(applicationContext);

        // 展示ListableBeanFactory.getBeansOfType() 是否安全的，是安全的
        displayListableBeanFactoryGetBeansOfType(applicationContext);

        // 展示ObjectProvider.stream()是否安全的，是安全的
        displayObjectProviderStream(applicationContext);


        applicationContext.close();
    }

    @Bean
    public User user() {
        return User.createUser();
    }

    @Bean
    public User user2(){
        User user = new User();
        user.setId(2L);
        user.setName("Tom");
        return user;
    }


    /**
     * 展示BeanFactory.getBean() 非异常安全的
     * 如果getBean一个不存在的Bean，抛异常：NoSuchBeanDefinitionException
     * 如果同一个类型的Bean多于一个，抛异常：NoUniqueBeanDefinitionException
     *
     * @param applicationContext
     */
    private static void displayBeanFactoryGetBean(AnnotationConfigApplicationContext applicationContext) {
        /*try {
            applicationContext.getBean(User.class);
        } catch (BeansException e) {
            e.printStackTrace();
        }*/

        // 改成Lambda表达式形式
        printBeansException("displayBeanFactoryGetBean", ()->applicationContext.getBean(User.class));
    }

    /**
     * 展示BeanFactory.getBean()是否是安全的
     * 如果getBean一个不存在的Bean，抛异常：NoSuchBeanDefinitionException
     * 如果同一个类型的Bean多于一个，抛异常：NoUniqueBeanDefinitionException
     *
     * @param applicationContext
     */
    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        /*try {
            beanProvider.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // 改成Lambda表达式形式
        printBeansException("displayObjectFactoryGetObject", beanProvider::getObject);
    }

    /**
     * 展示ObjectProvider.getIfAvailable() 是否安全的
     *
     * 如果getIfAvailable()不存在Bean，不抛异常
     * 如果getIfAvailable()存在多个类型相同的Bean，抛异常：NoUniqueBeanDefinitionException
     *
     * @param applicationContext
     */
    private static void displayObjectProviderGetIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderGetIfAvailable", beanProvider::getIfAvailable);
    }

    /**
     * 展示ListableBeanFactory.getBeansOfType() 是否安全的
     *
     *  不会抛NoSuchBeanDefinitionException，NoUniqueBeanDefinitionException异常
     *
     * @param applicationContext
     */
    private static void displayListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext applicationContext) {

        printBeansException("displayListableBeanFactoryGetBeansOfType", ()->applicationContext.getBeansOfType(User.class));
    }

    /**
     * 展示ObjectProvider.stream()是否安全的
     * 不会抛NoSuchBeanDefinitionException，NoUniqueBeanDefinitionException异常
     *
     * @param applicationContext
     */
    private static void displayObjectProviderStream(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);

        printBeansException("displayObjectProviderStream", beanProvider::stream);
    }

    /**
     * 打印异常栈信息
     *
     * @param runnable
     */
    private static void printBeansException(String soutce, Runnable runnable) {

        System.out.println("Source from：" + soutce);
        try {
            runnable.run();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
