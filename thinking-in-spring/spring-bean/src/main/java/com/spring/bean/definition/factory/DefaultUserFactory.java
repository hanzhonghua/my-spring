/**
 * Copyright (c) 2021 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2021/5/19 下午10:55
 */
package com.spring.bean.definition.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * User Factory 默认实现
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct 初始化...");
    }

    public void initUserFactory() {
        System.out.println("自定义 初始化...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet() 初始化...");
    }

    @PreDestroy
    public void destory() {
        System.out.println("PreDestroy 销毁...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destory() 销毁... ");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("DefaultUserFactory 对象正在被垃圾回收...");
    }

    public void destoryUserFactory(){
        System.out.println("自定义方法 销毁... ");
    }
}
