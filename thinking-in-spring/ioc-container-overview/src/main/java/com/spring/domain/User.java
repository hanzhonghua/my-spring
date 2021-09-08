/**
 * Copyright (c) 2020 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2020/8/23 下午5:50
 */
package com.spring.domain;

import com.spring.enums.City;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author HanZhonghua
 * @version 1.0
 */
@Data
public class User {

    private Long id;
    private String name;
    private City city;
    private City[] workCity;
    private List<City> lifeCity;
    private Map<String, City> cityMap;

}
