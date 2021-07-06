/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/6 下午10:08
 */
package com.spring.bean.definition;

import com.spring.bean.definition.factory.DefaultUserFactory;
import com.spring.bean.definition.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 外部单体对象来注册
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class SingletonBeanRegisrationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.refresh();


        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        UserFactory userFactory = new DefaultUserFactory();

        // 自创建对象注册到Spring
        beanFactory.registerSingleton("userFactory", userFactory);

        UserFactory userFactoryByLockUp = beanFactory.getBean("userFactory", UserFactory.class);

        System.out.println("userFactory == userFactoryByLockUp :" + (userFactory == userFactoryByLockUp));

        applicationContext.close();

    }
}
