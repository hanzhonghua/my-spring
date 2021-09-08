/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/25 下午5:01
 */
package com.spring.dependency.injection;

import com.spring.dependency.injection.annotation.UserGroup;
import com.spring.domain.SuperUser;
import com.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

/**
 * 基于 {@link org.springframework.beans.factory.annotation.Qualifier} 限定注入
 *
 * • 使用注解@Qualifier限定
 *      • 通过 Bean 名称限定
 *      • 通过分组限定
 * • 基于注解@Qualifier扩展限定
 *      • 自定义注解 - 如 Spring Cloud @LoadBalanced，完成特定分组的Bean
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired  // superUser --> @Primary 优先
    private User nomalUser;

    @Autowired  // user --> @Qualifier指定了Bean名称
    @Qualifier("user")
    private User pointUser;

    @Autowired  // 所有的user
    private Collection<User> allUsers;

    @Autowired  // @Qualifier Users
    @Qualifier
    private Collection<User> qualifierUsers;

    @Autowired // @UserGroup Users
    @UserGroup
    private Collection<User> userGroupUsers;

    @Bean
    @Qualifier
    private User user1() {
        User user = new User();
        user.setId(4L);
        user.setName("Qualifier1");
        return user;
    }

    @Bean
    @Qualifier
    private User user2() {
        User user = new User();
        user.setId(3L);
        user.setName("Qualifier2");
        return user;
    }

    @Bean
    @UserGroup
    private User user3() {
        User user = new User();
        user.setId(5L);
        user.setName("UserGroup1");
        return user;
    }

    @Bean
    @UserGroup
    private User user4() {
        User user = new User();
        user.setId(6L);
        user.setName("UserGroup2");
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        applicationContext.refresh();

        QualifierAnnotationDependencyInjectionDemo bean = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);
        System.out.println("nomalUser:" + bean.nomalUser);
        System.out.println("pointUser:" + bean.pointUser);
        System.out.println("allUsers:" + bean.allUsers);
        System.out.println("qualifierUsers:" + bean.qualifierUsers);
        System.out.println("userGroupUsers:" + bean.userGroupUsers);

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
