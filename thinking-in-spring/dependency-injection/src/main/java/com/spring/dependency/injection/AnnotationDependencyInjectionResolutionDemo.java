/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/25 下午9:44
 */
package com.spring.dependency.injection;

import com.spring.dependency.injection.annotation.InjectUser;
import com.spring.dependency.injection.annotation.MyAutowired;
import com.spring.domain.SuperUser;
import com.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

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
    private User user1;

    // 使用自定义注解
    @MyAutowired
    @Lazy
    private User user2;

    @Autowired
    private Map<String, User> userMap;

    @InjectUser
    private User injectUser;

    /**
     * 问题1
     * AnnotationConfigUtils 中169行，在注册 AutowiredAnnotationBeanPostProcessor 时，如果是遇到 AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     * 就不会再创建 AutowiredAnnotationBeanPostProcessor了，所以此实现之后 @Autowired 就不在生效了，如果想要生效，需要设置 autowiredAnnotationTypes
     * 或者Bean name 不要使用 AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
     *
     * 问题2
     * 这个方法如果不加static修饰，自定义注解和原生注解都不生效，加了之后就可以生效了，为什么？
     *
     * 如果不加static，想要获取自定义的 AutowiredAnnotationBeanPostProcessor，必须拿到demo bean，然后在执行注入。而注册各种 beanPostProcessors 是在 refresh 的
     * registerBeanPostProcessors 中，此时还没有开始初始化这个 demo bean。
     * 在初始化demo bean时，就开始处理注解了，但是此时自定义的 AutowiredAnnotationBeanPostProcessor 还没有被初始化，所以不认识注解，所以注入的信息都是null
     * 如果加了static，就不需要拿到demo bean，直接调用类方法获取自定义 AutowiredAnnotationBeanPostProcessor，在之后 demo bean初始化时候，就可以正常解析自定义的或者是
     * 原生注解了
     *
     * 问题3
     * 这种方式有什么用呢？
     *
     * 可以做一些扩展实现
     *
     * @return
     */
    /*@Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)*/
    @Bean
    public /*static*/ AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();

        beanPostProcessor.setAutowiredAnnotationType(InjectUser.class);

        /*Set<Class<? extends  Annotation>> classSet = new LinkedHashSet<>(asList(InjectUser.class, Autowired.class));
        beanPostProcessor.setAutowiredAnnotationTypes(classSet);*/
        return beanPostProcessor;
    }

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
        System.out.println(bean.user2);
        System.out.println(bean.userMap);
        System.out.println(bean.injectUser);

        applicationContext.close();
    }
}
