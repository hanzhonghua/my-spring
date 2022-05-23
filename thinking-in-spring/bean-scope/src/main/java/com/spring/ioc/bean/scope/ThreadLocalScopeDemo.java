/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/21 下午10:13
 */
package com.spring.ioc.bean.scope;

import com.spring.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 自定义 ThreadLocalScope 使用示例
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        });

        // 启动
        applicationContext.refresh();

        // 当线程就是单例的
        for (int i = 0; i < 3; i++) {
            User user = applicationContext.getBean("user", User.class);
            System.out.println(user);
        }

        // 多线程下每个线程对应的对象都是新建的
        for (int i = 0; i < 3; i++) {

            new Thread(() -> {
                User user = applicationContext.getBean("user", User.class);
                System.out.println("thread user:" + user);
            }).start();

        }


        applicationContext.close();
    }
}
