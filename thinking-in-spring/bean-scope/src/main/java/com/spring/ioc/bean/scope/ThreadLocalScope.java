/**
 * Copyright (c) 2022 XiaoMi Inc.All Rights Reserved.
 * Email: hanzhonghua1@xiaomi.com
 * Author: 韩忠华
 * Date:2022/5/21 下午9:57
 */
package com.spring.ioc.bean.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 ThreadLocalScope ，可参考Spring 内部自定义Scope SimpleThreadScope
 *
 * @author HanZhonghua
 * @version 1.0
 */
public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "thread-local";

    private final ThreadLocal<Map<String, Object>> threadScope = new NamedThreadLocal<Map<String, Object>>("ThreadLocalScope") {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        Map<String, Object> scope = this.threadScope.get();
        Object scopeObject = scope.get(name);
        if (scopeObject == null) {
            scopeObject = objectFactory.getObject();
            scope.put(name, scopeObject);
        }
        return scopeObject;
    }

    @Override
    public Object remove(String name) {

        Map<String, Object> scope = this.threadScope.get();
        return scope.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        Map<String, Object> scope = this.threadScope.get();
        return scope.get(key);
    }

    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}
