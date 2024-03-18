package com.kgc.exception;

import com.kgc.entity.Message;
import com.kgc.enums.ExceptionEnum;
import lombok.Getter;

/**
 * 登录异常类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-11:51
 */
@Getter
public class LoginException extends RuntimeException {
    protected Message msg;

    public LoginException() {
        super();
        msg = ExceptionEnum.LOGIN_ERROR.getMessage();
    }

    public LoginException(Message message) {
        super(message.getMessage());
        this.msg = message;
    }
}
