package com.lxz.fdfs.support;

import java.util.Map;

import org.springframework.core.NamedThreadLocal;

import com.google.common.collect.Maps;

/**
 * Created by xiaolezheng on 16/5/23.
 */
public class ContextHolder {
    private static final ThreadLocal<Map<String, Object>> context = new NamedThreadLocal("customer context");

    public static void set(String key, Object object) {
        if (context.get() == null) {
            Map<String, Object> content = Maps.newHashMap();
            context.set(content);
        }

        context.get().put(key, object);
    }

    public static Object get(String key) {
        return context.get().get(key);
    }

    public static void clean() {
        context.remove();
    }
}
