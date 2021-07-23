/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/7 下午10:55
 */
package com.spring.dependency.lookup;

import com.spring.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * ObjectProvider Spring 4.3之后提供的工具类，主要用来单个Bean实例查找
 *
 * @author HanZhonghua
 * @version 1.0
 */
// @Configuration  该注解是非必须的
public class ObjectProviderDemo {

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 ObjectProviderDemo 作为配置类
        applicationContext.register(ObjectProviderDemo.class);

        // 启动上下文
        applicationContext.refresh();

        // 依赖查找集合对象
        lookupByObjectProvider(applicationContext);

        // 依赖查找，如果没有对应的Bean，则走默认方式
        lookupIfAvailable(applicationContext);

        lookupByStreamOps(applicationContext);


        applicationContext.close();

    }

    @Bean
    @Primary
    public String helloWorld() { // 如果没有定义Bean的名称，方法名称就是
        return "Hello World";
    }

    @Bean
    public String message() {
        return "message";
    }

    /**
     * 同一类型Bean集合查找
     *
     * @param applicationContext
     */
    private static void  lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        /*Iterator<String> iterator = beanProvider.iterator();
        for (Iterator<String> it = iterator; it.hasNext(); ) {
            String str = it.next();
            System.out.println(str);
        }*/

        beanProvider.stream().forEach(System.out::println);
    }

    /**
     * 查找不存在的Bean，则走默认方式
     *
     * @param applicationContext
     */
    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        User user = beanProvider.getIfAvailable(User::createUser);
        System.out.println(user);
    }

    /**
     * 单一类型查找
     *
     * @param applicationContext
     */
    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);

        // 相同类型的Bean该方法之后返回一个，如果存在多个的话，则抛异常 NoUniqueBeanDefinitionException
        String object = beanProvider.getObject();
        System.out.println(object);
    }
}
