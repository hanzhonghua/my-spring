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
 * BeanDefinition Sprin的配置元信息接口
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 1);
        beanDefinitionBuilder.addPropertyValue("name", "Tom");



    }
}
