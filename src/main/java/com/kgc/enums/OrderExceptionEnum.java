package com.kgc.enums;

import lombok.Getter;

/**
 * 订单异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-21:46
 */
@Getter
public enum OrderExceptionEnum {
    ORDER_TABLE_STATUS("order status update failed", "订单状态更新失败!"),
    USER_NOT_EXIST("user does not exist", "用户不存在!"),
    CREATE_ORDER_ERROR("failed to create order", "创建订单失败!"),
    PRODUCT_GET_ERROR("commodity acquisition failed", "商品获取失败!"),
    PRODUCT_STOCK_LACK("insufficient inventory of goods", "商品库存不足!"),
    PRODUCT_UPDATE_ERROR("failed to update commodity information", "商品信息更新失败!"),
    ORDER_COST_UPDATE_ERROR("failed to update the total order price", "订单总价更新失败!"),
    ORDER_DETAIL_GET_ERROR("failed to get the order details", "订单详情获取失败!"),
    ORDER_DETAIL_CREATE_ERROR("failed to create order details", "订单详情创建失败!"),
    ORDER_DETAIL_DELETE_ERROR("failed to delete the order details", "订单详情删除失败!");

    private final String message;
    private final String msg;

    OrderExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
