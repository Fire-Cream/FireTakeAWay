package com.cream.fire_takeaway.common;

/**
 * @Author: Cream
 * @Date: 2022-09-25-21:11
 * @Description:基于ThreadLocal封装的工具类
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
