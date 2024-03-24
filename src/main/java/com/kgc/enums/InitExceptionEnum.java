package com.kgc.enums;

import lombok.Getter;

/**
 * 初始化异常类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-21:51
 */
@Getter
public enum InitExceptionEnum {
    INIT_OPENAPI_ERROR("alipay OpenAPI initialization failed", "支付宝OpenAPI初始化失败!"),
    INIT_APPID_ERROR("alipay AppId initialization failed", "支付宝AppId初始化失败!"),
    INIT_PUBLIC_KEY_ERROR("alipay PublicKey initialization failed", "支付宝PublicKey初始化失败!"),
    INIT_PRIVATE_KEY_ERROR("alipay PrivateKey initialization failed", "支付宝PrivateKey初始化失败!"),
    INIT_ALI_PUBLIC_KEY_ERROR("alipay AliPublicKey initialization failed", "支付宝AliPublicKey初始化失败!"),
    INIT_NOTIFY_URL_ERROR("alipay NotifyUrl initialization failed", "支付宝NotifyUrl初始化失败!"),
    INIT_RETURN_URL_ERROR("alipay ReturnUrl initialization failed", "支付宝ReturnUrl初始化失败!"),
    INIT_EMAIL_HOST_ERROR("email host initialization failed", "邮箱SMTP主机名初始化失败!"),
    INIT_EMAIL_USERNAME_ERROR("email username initialization failed", "邮箱发送账号初始化失败!"),
    INIT_EMAIL_PASSWORD_ERROR("email password initialization failed", "邮箱授权码初始化失败!"),
    INIT_TOKEN_SIGN_ERROR("token tokenSign initialization failed", "令牌签名初始化失败!"),
    ;

    private final String message;
    private final String msg;

    InitExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
