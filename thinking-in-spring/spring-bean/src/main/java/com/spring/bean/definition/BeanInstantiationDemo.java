package com.spring.bean.definition;

import com.spring.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 实例化实例
     • 常规方式
         • 通过构造器（配置元信息：XML、Java 注解和 Java API ）
         • 通过静态工厂方法（配置元信息：XML 和 Java API ）
         • 通过 Bean 工厂方法（配置元信息：XML和 Java API ）
         • 通过 FactoryBean（配置元信息：XML、Java 注解和 Java API ）
 * @author hanzhonghua@didichuxing.com
 * @date 2021/5/19
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        User bean = beanFactory.getBean("user-by-static-method", User.class);

        User userByInstanceMethod = beanFactory.getBean("user-by-instance-method", User.class);

        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println(bean);
        System.out.println(userByInstanceMethod);
        System.out.println(userByFactoryBean);

        System.out.println(bean == userByInstanceMethod);
        System.out.println(bean == userByFactoryBean);
    }
}
