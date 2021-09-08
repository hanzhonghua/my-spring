/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/24 下午10:54
 */
package com.spring.dependency.injection;

import com.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 注解依赖注入(方法注入) @Autowired @Resource @Bean
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class AnnotationDependencyMethodInjectionDemo {

    private UserHolder userHolder;
    private UserHolder userHolder2;

    @Autowired
    public void init1(UserHolder userHolder) {
        this.userHolder  =  userHolder;
    }

    @Resource
    public void init2(UserHolder userHolder) {
        this.userHolder2  =  userHolder;
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml文件，创建BeanDefinition
        reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        // 启动
        applicationContext.refresh();

        AnnotationDependencyMethodInjectionDemo bean = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);
        UserHolder userHolder = bean.userHolder;
        System.out.println(userHolder);

        UserHolder userHolder2 = bean.userHolder2;
        System.out.println(userHolder2);

        System.out.println(userHolder == userHolder2);

        applicationContext.close();


    }

}
