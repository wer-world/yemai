package com.kgc.enums;

import lombok.Getter;

/**
 * 邮箱异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-14:49
 */
@Getter
public enum EmailExceptionEnum {
    MAIL_OBJECT_CREATE_FAILURE("mail object creation failed", "邮件对象创建失败!"),
    REGISTER_STATUS_ERROR("registration failed", "注册失败!"),
    ID_CARD_ERROR("id card already exists", "身份证已存在!");


    private final String message;
    private final String msg;

    EmailExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
