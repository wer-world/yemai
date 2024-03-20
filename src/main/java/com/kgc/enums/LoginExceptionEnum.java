package com.kgc.enums;

import lombok.Getter;

/**
 * 登录错误消息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-11:38
 */
@Getter
public enum LoginExceptionEnum {
    LOGIN_NAME_ERROR("login already exists","登录名已存在!"),
    ID_CARD_ERROR("id card already exists","身份证已存在!");

    private final String message;
    private final String msg;

    LoginExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
