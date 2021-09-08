/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/25 下午9:44
 */
package com.spring.dependency.injection;

import com.spring.domain.SuperUser;
import com.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

/**
 * 注解驱动依赖注入原理
 * • 入口 - DefaultListableBeanFactory#resolveDependency
 * • 依赖描述符 - DependencyDescriptor
 * • 自定绑定候选对象处理器 - AutowireCandidateResolver
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    private User user1 = new User();

    @Autowired
    @Lazy
    private User user2;

    @Autowired
    private Map<String, User> userMap;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml文件，创建BeanDefinition
        reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        // 启动
        applicationContext.refresh();

        AnnotationDependencyInjectionResolutionDemo bean = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
        System.out.println(bean.user1);

        applicationContext.close();
    }
}
