/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/24 下午8:52
 */
package com.spring.dependency.injection;

import com.spring.domain.User;
import lombok.Data;

/**
 * @author HanZhonghua
 * @version 1.0
 */
@Data
public class UserHolder {

    private User user;

    public UserHolder(){

    }

    public UserHolder(User  user) {
        this.user = user;
    }
}
