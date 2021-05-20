package com.spring.bean.definition;

import com.spring.bean.definition.factory.UserFactory;
import com.spring.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Bean 实例化实例
     • 特殊方式
         • 通过 ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和 Java API ）
         • 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
         • 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
 * @author hanzhonghua@didichuxing.com
 * @date 2021/5/19
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");

        serviceLoaderDemo();

    }

    public static void serviceLoaderDemo() {
        ServiceLoader<UserFactory> load = ServiceLoader.load(UserFactory.class);

        Iterator<UserFactory> iterator = load.iterator();
        while (iterator.hasNext()) {
            UserFactory next = iterator.next();
            System.out.println(next);
        }
    }
}
