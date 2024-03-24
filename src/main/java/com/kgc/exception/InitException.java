package com.kgc.exception;

import lombok.Getter;

/**
 * 自定义初始化异常
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-21:55
 */
@Getter
public class InitException extends RuntimeException {
    private String field; // 错误字段
    private String value; // 错误值

    public InitException() {
    }

    public InitException(String message, String field, String value) {
        super(message);
        this.field = field;
        this.value = value;
    }
}
