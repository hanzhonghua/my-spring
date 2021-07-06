/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/6/6 下午4:46
 */
package com.spring.bean.definition;

import com.spring.bean.definition.factory.DefaultUserFactory;
import com.spring.bean.definition.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean GC
 *
 * 1. 关闭 Spring 容器（应用上下文）
 * 2. 执行 GC
 * 3. Spring Bean 覆盖的 finalize() 方法被回调
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class BeanGCDemo {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 注册Configuration Class，相当于注册Bean了
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();

        applicationContext.close();

        Thread.sleep(1000);
        System.gc();
    }

}
