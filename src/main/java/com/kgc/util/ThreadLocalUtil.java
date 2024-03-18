package com.kgc.util;

/**
 * ThreadLocal优化工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-23:15
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 根据键获取值
     *
     * @param <T> 返回类型
     * @return 返回值
     */
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    /**
     * 存储值
     *
     * @param value 值
     */
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    /**
     * 清楚ThreadLocal 防止内存泄漏
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
