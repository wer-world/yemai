package com.kgc.exception;

import lombok.Getter;

/**
 * 登录异常类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-11:51
 */
@Getter
public class LoginException extends RuntimeException {
    private String msg;

    public LoginException() {
    }

    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
