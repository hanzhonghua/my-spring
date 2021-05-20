/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/5/19 下午10:54
 */
package com.spring.bean.definition.factory;

import com.spring.domain.User;

/**
 * @author HanZhonghua
 * @version 1.0
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
