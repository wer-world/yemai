package com.kgc.exception;

import lombok.Getter;

/**
 * 登录异常类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-11:48
 */
@Getter
public class LoginException extends RuntimeException{
    private String msg;

    public LoginException() {
    }

    public LoginException(String message,String msg) {
        super(message);
        this.msg = msg;
    }
}
