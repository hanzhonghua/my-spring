/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/5/31 上午8:54
 */
package com.spring.bean.definition.factory;

import com.spring.domain.User;

/**
 * @author HanZhonghua
 * @version 1.0
 */
public class XiaoMingUserFactory implements UserFactory {

    @Override
    public User createUser() {
        User user = new User();
        user.setId(2L);
        user.setName("小明");
        return user;
    }
}
