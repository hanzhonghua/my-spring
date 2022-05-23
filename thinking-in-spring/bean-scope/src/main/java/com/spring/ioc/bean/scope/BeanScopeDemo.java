/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/17 下午10:55
 */
package com.spring.ioc.bean.scope;

import com.spring.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * Bean 作用域介绍（scope）
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanScopeDemo implements DisposableBean {

    @Bean
    // 默认作用域就是单例的，注意这个单例指的是BeanDefinition.isSingletion()字段，而不是注册的单例Bean
    public static User singletonUser() {
        return createUser();
    }

    @Bean
    // 指定为prototype类型，每次依赖注入或依赖查找都会重新创建 Bean 实例
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;
    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;
    @Autowired
    private Map<String, User> users;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 当前类作为配置类
        applicationContext.register(BeanScopeDemo.class);

        // 自定义 BeanPostProcessor，用来在Bean 初始化之后做一些事情，但是这里不适合用来销毁 prototype Bean，
        // 因为该方法还需要返回 Bean 对象，可以实现 DisposableBean完成销毁
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    System.out.println(bean.getClass().getName() + " Bean name: " + beanName + " 在初始化后被回调");
                    return bean;
                }
            });
        });

        // 启动
        applicationContext.refresh();

        /*
         * 结论：
         * 1.通过测试我们发现依赖查找和依赖注入 singleton Bean 都是同一个对象，而prototype Bean每次都会创建新的对象
         * 即便是注入 Collection ，prototype类型的 Bean 也不会复用之前注入的同一个 prototype Bean 的对象
         *
         * 2.通过在 User 类中增加 @PostConstruct 和 @PreDestroy，发现 singleton 和 prototype Bean都会执行初始化方法回调
         * 但是 prototype Bean 不会执行销毁方法回调，就是Spring 不会管理 prototype Bean 的销毁，如果有场景使用 prototype Bean
         * 的话，一定要注意手动销毁，可以利用 BeanPostProcessor
         */
        // 依赖查找作用域
        scopeBeansByLookup(applicationContext);
        // 依赖注入作用域
        scopeBeansByInjection(applicationContext);

        applicationContext.close();
    }

    /**
     * Bean 依赖查找作用域
     *
     * @param applicationContext
     */
    private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext) {

        for (int i = 0; i < 3; i++) {
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser:" + singletonUser);
        }

        for (int i = 0; i < 3; i++) {
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println("prototypeUser:" + prototypeUser);
        }
    }

    /**
     * Bean 依赖注入作用域
     *
     * @param applicationContext
     */
    private static void scopeBeansByInjection(AnnotationConfigApplicationContext applicationContext) {

        BeanScopeDemo bean = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println("bean.singletonUser:"+bean.singletonUser);
        System.out.println("bean.singletonUser1:"+bean.singletonUser1);
        System.out.println("bean.prototypeUser:"+bean.prototypeUser);
        System.out.println("bean.prototypeUser1:"+bean.prototypeUser1);
        System.out.println("bean.users:"+bean.users);
    }

    @Override
    public void destroy() throws Exception {

        System.out.println("当前 BeanScopeDemo Bean 正在销毁中...");
        this.prototypeUser.destroy();
        this.prototypeUser1.destroy();

        for (Map.Entry<String, User> entity : users.entrySet()) {
            String beanName = entity.getKey();
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) {
                entity.getValue().destroy();
            }
        }
        System.out.println("当前 BeanScopeDemo Bean 销毁完成");
    }
}
