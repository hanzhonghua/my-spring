/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/10/12 下午10:41
 */
package com.spring.bean.definition;

import com.spring.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * BeanDefinition Spring的配置元信息接口
 * 定义的有：
 * Bean 的类名，作用域，属性等
 * <p>
 * Class - Bean 全类名，必须是具体类，不能用抽象类或接口
 * Name -  Bean 的名称或者 ID
 * Scope -  Bean 的作用域（如：singleton、prototype 等）
 * Constructor -  arguments Bean 构造器参数（用于依赖注入）
 * Properties -  Bean 属性设置（用于依赖注入）
 * Autowiring mode -  Bean 自动绑定模式（如：通过名称 byName）
 * Lazy initialization mode -  Bean 延迟初始化模式（延迟和非延迟）
 * Initialization method - Bean 初始化回调方法名称
 * Destruction method - Bean 销毁回调方法名称
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {

        // 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);

        // 添加属性
        beanDefinitionBuilder.addPropertyValue("id", 1);
        beanDefinitionBuilder.addPropertyValue("name", "Tom");

    }
}
