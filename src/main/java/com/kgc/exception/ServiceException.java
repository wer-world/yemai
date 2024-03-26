package com.kgc.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-21:37
 */
@Getter
public class ServiceException extends RuntimeException {
    private String msg;

    public ServiceException() {
    }

    public ServiceException(String message,String msg) {
        super(message);
        this.msg = msg;
    }
}
