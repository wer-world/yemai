package com.kgc.enums;

import lombok.Getter;

/**
 * 异常错误枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-23:03
 */
@Getter
public enum TokenExceptionEnum {
    // 数据操作错误定义
    BODY_NOT_MATCH("the requested data parameters do not match","请求的数据参数不符!"),
    SIGNATURE_NOT_MATCH("the requested digital signature is invalid","请求的数字签名失效!"),
    NOT_TOKEN("the request does not carry a token","请求没有携带令牌!"),
    LOGIN_ERROR("login failed","登录失败!"),
    TOKEN_OVER("token expired","token过期!"),
    VISA_ALGORITHM("the verification key algorithm is inconsistent","验证秘钥算法不一致!"),
    ILLEGAL_REQUEST("illegal request, please log in and operate","非法请求,请登录后操作!");

    private final String message;
    private final String msg;

    TokenExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }

}
