/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/7/25 下午3:24
 */
package com.spring.enums;

import lombok.Getter;

/**
 * 城市 Enum
 *
 * @author HanZhonghua
 * @version 1.0
 */
@Getter
public enum  City {

    HENAN(1),
    ANYANG(2),
    BEIJING(3),
    HANGZHOU(4),
    ;

    private int code;

    City(int code) {
        this.code = code;
    }
}
