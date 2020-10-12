/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/8/16 下午11:04
 */
package com.ioc.java.beans;

/**
 * get set 方法
 * 对应到Java Beans中是Write 与 Read
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class Person {

    private String name;  // Property

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
