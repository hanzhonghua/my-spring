/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/9/22 下午11:08
 */
package com.spring.container;

import com.spring.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * Ioc 容器
 * 基于 XML Ioc容器，当然没有了Aop，Event等机制，比较简单
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanFactoryAsIocContainer {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml 配置文件路径
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载配置，返回加载Bean的个数
        int i = reader.loadBeanDefinitions(location);
        System.out.println(i);
        getListByType(beanFactory);

    }

    private static void getListByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("根据Bean类型查找多个Bean："+beansOfType);
        }
    }
}
