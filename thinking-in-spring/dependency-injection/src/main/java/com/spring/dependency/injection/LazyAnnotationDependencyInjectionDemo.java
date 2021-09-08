/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/25 下午5:01
 */
package com.spring.dependency.injection;

import com.spring.domain.SuperUser;
import com.spring.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 基于 {@link org.springframework.beans.factory.ObjectProvider} 延迟注入
 *
 *     • 使用API ObjectFactory延迟注入
 *         • 单一类型
 *         • 集合类型
 *     • 使用API ObjectProvider延迟注入(推荐)
 *         • 单一类型
 *         • 集合类型
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired  // 普通实时注入 superUser --> @Primary 优先
    @Lazy
    private User nomalUser;

    @Autowired
    private ObjectProvider<User> userObjectProvider;

    @Autowired
    private ObjectProvider<List<User>> userObjectProviders;


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        applicationContext.refresh();

        LazyAnnotationDependencyInjectionDemo bean = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);
        System.out.println("nomalUser:" + bean.nomalUser);
        System.out.println("ObjectProvider Lazy User:" + bean.userObjectProvider.getObject());
        bean.userObjectProvider.forEach(System.out::println);

        System.out.println("ObjectProvider Lazy User List:" + bean.userObjectProviders.getObject());

        applicationContext.close();
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("user");
        return user;
    }

    @Bean
    @Primary
    public User superUser() {
        User user = new SuperUser();
        user.setId(2L);
        user.setName("superUser");
        return user;
    }

}
