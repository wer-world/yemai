package com.kgc.enums;

import lombok.Getter;

/**
 * 订单详情异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-10:14
 */
@Getter
public enum OrderDetailExceptionEnum {
    ORDER_DETAIL_ADD_FAILURE("order status update failed", "订单详情信息添加失败!"),
    ORDER_DETAIL_DELETE_FAILURE("failed to delete the order details", "订单详情信息删除失败!");
    private final String message;
    private final String msg;

    OrderDetailExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
