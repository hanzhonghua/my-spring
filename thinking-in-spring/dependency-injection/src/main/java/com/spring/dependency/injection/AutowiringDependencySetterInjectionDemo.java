/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/24 下午6:57
 */
package com.spring.dependency.injection;


import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 通过byName / byType 自动注入
 * <Bean/> 标签的autowire属性，xml形式常用
 *
 * 该注入模式官方并不推荐使用
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class AutowiringDependencySetterInjectionDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        String path = "classpath:/META-INF/autowiring-dependency-setter-injection.xml";

        // 加载xml文件，解析并生成BeanDefinition
        reader.loadBeanDefinitions(path);

        // 依赖查找与创建Bean
        UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);

    }
}
