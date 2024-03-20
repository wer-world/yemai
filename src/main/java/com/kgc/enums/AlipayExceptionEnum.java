package com.kgc.enums;

import lombok.Getter;

/**
 * 支付宝异常枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-21:46
 */
@Getter
public enum AlipayExceptionEnum {
    ALIPAY_TABLE_STATUS("exception in updating Alipay status table","支付宝状态表更新异常!"),
    ALIPAY_CREATE_ERROR("order creation failed","订单创建失败!");

    private final String message;
    private final String msg;

    AlipayExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
