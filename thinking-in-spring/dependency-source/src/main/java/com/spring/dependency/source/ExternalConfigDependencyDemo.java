/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/14 下午10:43
 */
package com.spring.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 外部配置作为依赖注入来源
 *
 * @author HanZhonghua
 * @version 1.0
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties", encoding = "UTF-8")
public class ExternalConfigDependencyDemo {

    /**
     * @Value 注解的注入处理也是在 AutowiredAnnotationBeanPostProcessor 中处理的，可以在构造方法中看到处理 @Value
     * AutowiredAnnotationBeanPostProcessor.inject() => DefaultListableBeanFactory.resolveDependency().doResolveDependency()
     * 方法中的 getAutowireCandidateResolver().getSuggestedValue() 代码读取 @Value 注入的属性，这个是会把${user.id:-1}都会读取
     * 然后通过 resolveEmbeddedValue() 完整解析替换
     *
     */
    @Value("${user.id:-1}")
    private Long id;

    @Value("${usr.name}")
    private String name;

    @Value("${user.location:classpath://default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(ExternalConfigDependencyDemo.class);

        // 启动
        applicationContext.refresh();

        ExternalConfigDependencyDemo bean = applicationContext.getBean(ExternalConfigDependencyDemo.class);
        System.out.println("user.id=" + bean.id);
        System.out.println("usr.name=" + bean.name);
        System.out.println("user.resource=" + bean.resource);

        applicationContext.close();
    }
}
