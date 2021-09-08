/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/24 下午10:54
 */
package com.spring.dependency.injection;

import com.spring.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Spring API 依赖注入(BeanDefinition)
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class ApiDependencySetterInjectionDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 创建 UserHolder BeanDefinition
        BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();

        // 注册 UserHolder BeanDefinition
        applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml文件，创建BeanDefinition
        reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        // 启动
        applicationContext.refresh();

        UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        applicationContext.close();

    }

    public static BeanDefinition createUserHolderBeanDefinition() {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);

        /*
            <bean class="com.spring.dependency.injection.UserHolder">
                <!--setter 注入-->
                <property name="user" ref="superUser"/>
            </bean>
        相当于 <property name="user" ref="superUser"/> 注入
         */
        builder.addPropertyReference("user", "superUser");
        return builder.getBeanDefinition();
    }

    /*@Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }*/
}
