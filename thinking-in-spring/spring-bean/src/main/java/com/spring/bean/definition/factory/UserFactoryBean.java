/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/5/19 下午11:01
 */
package com.spring.bean.definition.factory;

import com.spring.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * 通过 FactoryBean 实例化
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class UserFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
