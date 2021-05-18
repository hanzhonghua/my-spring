Spring Ioc容器概述

1. Spring IoC 依赖查找
    
        • 根据 Bean 名称查找 
            • 实时查找
            • 延迟查找
        • 根据 Bean 类型查找
            • 单个 Bean 对象
            • 集合 Bean 对象
        • 根据 Bean 名称 + 类型查找
        • 根据 Java 注解查找
            • 单个 Bean 对象
            • 集合 Bean 对象
2. Spring IoC 依赖注入

        • 根据 Bean 名称注入
        • 根据 Bean 类型注入
            • 单个 Bean 对象
            • 集合 Bean 对象
        • 注入容器內建 Bean 对象
        • 注入非 Bean 对象
            • 注入类型
            • 实时注入 • 延迟注入
3. Spring IoC 依赖来源

        • 自定义 Bean
        • 容器內建 Bean 对象 
        • 容器內建依赖
        
4. Spring IoC 配置元信息

        • Bean 定义配置
            • 基于 XML 文件
            • 基于 Properties 文件
            • 基于 Java 注解
            • 基于 Java API(专题讨论)
        • IoC 容器配置
            • 基于 XML 文件
            • 基于 Java 注解
            • 基于 Java API (专题讨论) • 外部化属性配置
        • 基于 Java 注解
5. Spring IoC 容器

        • BeanFactory 和 ApplicationContext 谁才是 Spring IoC 容器?
6. Spring 应用上下文

        • ApplicationContext 除了 IoC 容器角色，还有提供:
            • 面向切面(AOP)
            • 配置元信息(Configuration Metadata)
            • 资源管理(Resources)
            • 事件(Events)
            • 国际化(i18n)
            • 注解(Annotations)
            • Environment 抽象(Environment Abstraction)
            https://docs.spring.io/spring/docs/5.2.2.RELEASE/spring-framework-reference/core.html#beans-introduction
7. 使用 Spring IoC 容器

        • BeanFactory 是 Spring 底层 IoC 容器
        • ApplicationContext 是具备应用特性的 BeanFactory 超集
8. Spring IoC 容器生命周期

        • 启动 
        • 运行 
        • 停止
9. 面试题精选

        沙雕面试题 - 什么是 Spring IoC 容器?
        答:Spring Framework implementation of the Inversion of Control (IoC) principle. IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean.
 
 
        996 面试题 - BeanFactory 与 FactoryBean?
        答:
        BeanFactory 是 IoC 底层容器
        FactoryBean 是 创建 Bean 的一种方式，帮助实现复杂的初始化逻辑
 

        劝退面试题 - Spring IoC 容器启动时做了哪些准备?
        答:IoC 配置元信息读取和解析、IoC 容器生命周期、Spring 事件发布、国 际化等，更多答案将在后续专题章节逐一讨论

