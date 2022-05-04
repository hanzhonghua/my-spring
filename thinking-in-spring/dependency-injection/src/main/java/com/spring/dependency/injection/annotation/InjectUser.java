/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/4/28 下午10:16
 */
package com.spring.dependency.injection.annotation;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * @author HanZhonghua
 * @version 1.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectUser {

}
