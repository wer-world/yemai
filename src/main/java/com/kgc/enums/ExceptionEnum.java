package com.kgc.enums;

import com.kgc.entity.Message;
import lombok.Getter;

/**
 * 异常错误枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-23:03
 */
@Getter
public enum ExceptionEnum {
    // 数据操作错误定义
    BODY_NOT_MATCH(Message.tokenError("请求的数据参数不符!")),
    SIGNATURE_NOT_MATCH(Message.tokenError("请求的数字签名失效!")),
    NOT_TOKEN(Message.tokenError("请求没有携带令牌!")),
    LOGIN_ERROR(Message.tokenError("登录失败!")),
    TOKEN_OVER(Message.tokenError("token过期!")),
    VISA_ALGORITHM(Message.tokenError("验证秘钥算法不一致!")),
    ILLEGAL_REQUEST(Message.illegalRequest());
    private final Message message;

    ExceptionEnum(Message message) {
        this.message = message;
    }

}
