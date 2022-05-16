/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/11 下午10:53
 */
package com.spring.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * ResolvableDependency 作为依赖来源
 * 通过 ConfigurableListableBeanFactory.registerResolvableDependency() 进行依赖注入，注意此注入不能用于依赖查找，且只能用于类型注入
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init() {
        System.out.println(value);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(ResolvableDependencySourceDemo.class);

        // <1> 可以直接获取 getBeanFactory()，然后  registerResolvableDependency
        ConfigurableListableBeanFactory configurableListableBeanFactory = applicationContext.getBeanFactory();
        configurableListableBeanFactory.registerResolvableDependency(String.class, "Hello, World");

        // <2> 通过添加一个回调来进行注册，会在refresh().invokeBeanFactoryPostProcessors()进行回调处理
        /*applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class, "Hello, World");
        });*/

        // 启动
        applicationContext.refresh();

        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        /*if (beanFactory instanceof ConfigurableListableBeanFactory) {
            ConfigurableListableBeanFactory configurableListableBeanFactory = ConfigurableListableBeanFactory.class.cast(beanFactory);

            // 通过这种注入会报错，因为在applicationContext.refresh()中就开始注入ResolvableDependencySourceDemo，而 private String value 是强制注入的
            // 此时也会处理，而下面的这行代码还未执行的，既是注入的来源来没有准备好，会报错：NoSuchBeanDefinitionException No qualifying bean of type 'java.lang.String' available
            // 如何解决这个问题呢，可以通过添加 BeanFactoryPostProcessor 来完成

            // 看上面代码 <1> <2> 解决代码
            configurableListableBeanFactory.registerResolvableDependency(String.class, "Hello, World");
        }*/

        applicationContext.close();
    }
}
