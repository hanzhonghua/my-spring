/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/10/13 下午11:24
 */
package com.spring.bean.definition;

import com.spring.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 通过注解方式注册 Bean 示例
 * <p>
 * BeanDefinition 注册
 * • XML 配置元信息
 * • <bean name=”...” ... />
 * • Java 注解配置元信息
 * • @Bean
 * • @Component
 * • @Import
 * 通过下面实验发现同一个Bean多次注入也会只有一个生效，Spring容器中不会重复注册
 * • Java API 配置元信息
 * • 命名方式：BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
 * • 非命名方式：
 * BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition,Be
 * anDefinitionRegistry)
 * • 配置类方式：AnnotatedBeanDefinitionReader#register(Class...)
 * <p>
 * 外部单例对象注册
 * • Java API 配置元信息
 * • SingletonBeanRegistry#registerSingleton
 *
 * @author HanZhonghua
 * @version 1.0
 */
// 注解方式三：@Import
@Import(AnnoationBeanDefinitionDemo.Config.class)
public class AnnoationBeanDefinitionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册Configuration Class（配置类），相当于注册Bean了
        applicationContext.register(AnnoationBeanDefinitionDemo.class);

        // API 配置
        registerUserBeandefinition(applicationContext, "Garry");
        registerUserBeandefinition(applicationContext);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        Map<String, User> beansOfType = applicationContext.getBeansOfType(User.class);
        Map<String, Config> beansOfType1 = applicationContext.getBeansOfType(Config.class);

        // 这里没有出现Bean的别名，因为别名并不是一个真正意义的Bean
        System.out.println("User类型 所有的Bean："+beansOfType);
        System.out.println("Config类型所以的Bean："+beansOfType1);

        // 显示关闭 Spring 应用上下文
        applicationContext.close();

    }

    /**
     * API 命名注册方式
     *
     * @param registry
     * @param beanName
     */
    public static void registerUserBeandefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 为Bean属性赋值
        beanDefinitionBuilder.addPropertyValue("id", 1).addPropertyValue("name", "Garry");

        if (StringUtils.isEmpty(beanName)) {
            // 没有命名，使用系统默认方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        } else {
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }

    }

    /**
     * API 非命名方式
     *
     * @param registry
     */
    public static void registerUserBeandefinition(BeanDefinitionRegistry registry) {

        registerUserBeandefinition(registry, null);
    }


    // 注解方式二：@Component
    @Component
    public static class Config {

        // 注解方式一：通过 @Bean 创建
        @Bean(name = {"user", "my-user"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("Tom");
            return user;

        }
    }
}
