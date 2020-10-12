/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/8/23 下午5:34
 */
package com.spring.dependency.lookup;

import com.spring.annotation.Super;
import com.spring.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Spring Ioc 依赖查找Demo
 * @author HanZhonghua
 * @version 1.0
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {

        // 配置xml，启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("/META-INF/dependency-lookup-context.xml");

        // 根据Bean名称实时查找实时查找
        getByRealTime(beanFactory);
        // 根据Bean名称实时查找 -> 延迟查找
        getByLazy(beanFactory);

        // 根据Bean类型查找 -> 单个Bean
        getSingleByType(beanFactory);
        // 根据Bean类型查找 -> 多个Bean
        getListByType(beanFactory);

        // 根据注解查找
        getByAnnotation(beanFactory);

    }

    /**
     * 根据Bean名称实时查找
     * 项目启动之后时候就已经创建好Bean了
     *
     * @param beanFactory
      */
    private static void getByRealTime (BeanFactory beanFactory){
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找："+user);
    }

    /**
     * 根据Bean名称延迟查找
     * 只有在查询时候才会创建，使用的是 ObjectFactory
     *
     * @param beanFactory
     */
    private static void getByLazy(BeanFactory beanFactory) {

        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User object = objectFactory.getObject();
        System.out.println("延迟查找："+object);
    }

    /**
     * 根据Bean类型查找 -> 单个Bean
     *
     * @param beanFactory
     */
    private static void getSingleByType(BeanFactory beanFactory) {
        User bean = beanFactory.getBean(User.class);
        System.out.println("根据类型查找单个Bean："+bean);
    }

    /**
     * 根据Bean类型查找 -> 多个Bean
     *
     * @param beanFactory
     */
    private static void getListByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("根据Bean类型查找多个Bean："+beansOfType);
        }
    }

    /**
     * 根据Java注解查找
     *
     * @param beanFactory
     */
    private static void getByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> beansOfType = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("根据注解查找多个Bean："+beansOfType);
        }
    }
}
