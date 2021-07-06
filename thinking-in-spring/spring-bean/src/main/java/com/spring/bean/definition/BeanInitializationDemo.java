/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/6/6 下午4:46
 */
package com.spring.bean.definition;

import com.spring.bean.definition.factory.DefaultUserFactory;
import com.spring.bean.definition.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 初始化 Demo
 *
 * • @PostConstruct 标注方法
 * • 实现 InitializingBean 接口的 afterPropertiesSet() 方法，比较常用的还是这种方法
 * • 自定义初始化方法
 *      • XML 配置：<bean init-method=”init” ... />
 *      • Java 注解：@Bean(initMethod=”init”)
 *      • Java API：AbstractBeanDefinition#setInitMethodName(String)
 *
 * @author HanZhonghua
 * @version 1.0
 */
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 注册Configuration Class，相当于注册Bean了
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();

        // Spring 容器启动完成
        System.out.println("Spring 启动成功...");

        applicationContext.getBean(UserFactory.class);

        System.out.println("Spring 准备关闭...");
        // close() 中完成对象的销毁
        applicationContext.close();
        System.out.println("Spring 已经关闭...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "destoryUserFactory")
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
