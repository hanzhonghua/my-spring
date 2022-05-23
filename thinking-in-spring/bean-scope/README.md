# Spring Bean 作用域

1. Spring Bean 作用域
2. "singleton" Bean 作用域
3. "prototype" Bean 作用域
4. "request" Bean 作用域
5. "session" Bean 作用域
6. "application" Bean 作用域
7. 自定义 Bean 作用域
8. 课外资料
9. 面试题精选

## Spring Bean 作用域
• 作用域

|来源   | 说明 |
|---|---|
| singleton  |默认 Spring Bean 作用域，一个 BeanFactory 有且仅有一个实例 |
|  prototype |原型作用域，每次依赖查找和依赖注入生成新 Bean 对象 |
| request  | 将 Spring Bean 存储在 ServletRequest 上下文中|
|  session | 将 Spring Bean 存储在 HttpSession 中|
| application  |将 Spring Bean 存储在 ServletContext 中 |


## "singleton" Bean 作用域
• 在同一个BeanFactory中，同一个Bean的实例只有一份

## "prototype" Bean 作用域
• 在每次依赖注入或依赖查找时，返回的都是新实例

• 注意事项

• Spring 容器没有办法管理 prototype Bean 的完整生命周期，也没有办法记录示例的存在。销毁回调方法将不会执行，可以利用 BeanPostProcessor 进行清扫工作。

## "request" Bean 作用域
• 配置

    • XML - <bean class= "..." scope = "request" />

• Java 注解 - @RequestScope 或 @Scope(WebApplicationContext.SCOPE_REQUEST)

• 实现

• API - RequestScope

## "session" Bean 作用域
• 配置

    • XML - <bean class= "..." scope = "session" />

• Java 注解 - @RequestScope 或 @Scope(WebApplicationContext.SCOPE_REQUEST)

• 实现

• API - SessionScope

## "application" Bean 作用域
• 配置

    • XML - <bean class= "..." scope = "application" />

• Java 注解 - @ApplicationScope 或 @Scope(WebApplicationContext.SCOPE_APPLICATION)

• 实现

• API - ApplicationScope

## 3 自定义 Bean 作用域
• 实现 Scope

• org.springframework.beans.factory.config.Scope

• 注册 Scope

• API - org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope

• 配置

    <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
    <property name="scopes">
    <map>
    <entry key="...">
    </entry>
    </map>
    </property>
    </bean>

## 课外资料
• Spring Cloud RefreshScope 是如何控制 Bean 的动态刷新？

## 面试题
### 沙雕面试题 - Spring 內建的 Bean 作用域有几种？
答：singleton、prototype、request、session、application 以及websocket


### 996 面试题 - singleton Bean 是否在一个应用是唯一的？
答：否，singleton bean 仅在当前 Spring IoC 容器（BeanFactory）中是单例对象。

### 劝退面试题 - “application”Bean 是否被其他方案替代
答：可以的，实际上，“application” Bean 与“singleton” Bean 没有
本质区别