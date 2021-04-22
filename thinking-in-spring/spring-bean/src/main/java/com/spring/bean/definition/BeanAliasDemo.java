/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/10/13 下午10:12
 */
package com.spring.bean.definition;

import com.spring.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名相关
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/bean-definitions-context.xml");
        User user = applicationContext.getBean("user", User.class);
        User bean = applicationContext.getBean("my-user", User.class);

        System.out.println(user == bean);
    }
}
