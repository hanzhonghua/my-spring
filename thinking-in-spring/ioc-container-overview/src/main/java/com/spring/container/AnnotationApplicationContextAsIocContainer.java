/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/9/23 下午10:15
 */
package com.spring.container;

import com.spring.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author HanZhonghua
 * @version 1.0
 */
public class AnnotationApplicationContextAsIocContainer {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类注册
        annotationConfigApplicationContext.register(AnnotationApplicationContextAsIocContainer.class);
        // 启动应用上下文
        annotationConfigApplicationContext.refresh();


        getListByType(annotationConfigApplicationContext);
    }

    private static void getListByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("根据Bean类型查找多个Bean："+beansOfType);
        }
    }

    /**
     * 通过 Java 方式定义一个 Bean
     * @return
     */
    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("Tom");
        return user;
    }
}
