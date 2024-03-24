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
    ALIPAY_CREATE_FAILURE("order creation failed","订单创建失败!"),
    ALIPAY_ORDER_NOT_EXIST("order does not exist","订单不存在!"),
    ALIPAY_CREATE_ERROR("order creation error","订单创建错误!"),
    ALIPAY_CONFIRM_SIGN_FAILURE("failed to sign!","验签失败!"),
    ALIPAY_CONFIRM_SIGN_ERROR("error in signing inspection!","验签错误!"),
    ALIPAY_TRANSACTION_FAILURE("transaction failure!","交易失败!"),
    ALIPAY_ADD_PAYMENT_FAILURE("adding payment message failed!","添加支付消息失败!");

    private final String message;
    private final String msg;

    AlipayExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
