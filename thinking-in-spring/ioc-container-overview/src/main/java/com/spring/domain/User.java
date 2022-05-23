/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/8/23 下午5:50
 */
package com.spring.domain;

import com.spring.enums.City;
import lombok.Data;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * @author HanZhonghua
 * @version 1.0
 */
@Data
public class User implements BeanNameAware {

    private Long id;
    private String name;
    private City city;
    private City[] workCity;
    private List<City> lifeCity;
    private Map<String, City> cityMap;

    // Bean name不需要序列化和反序列化
    private transient String beanName;

    public static User createUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Lili");
        return user;
    }

    @PostConstruct
    public void init() {
        System.out.println("user Bean ["+ beanName +"] 对象初始化...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("user Bean ["+ beanName +"] 对象销毁中...");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
