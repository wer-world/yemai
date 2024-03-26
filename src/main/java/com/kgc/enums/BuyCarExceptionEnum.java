package com.kgc.enums;

import lombok.Getter;

/**
 * 购物车异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-10:29
 */
@Getter
public enum BuyCarExceptionEnum {
    DELETE_PRODUCT_FAILURE("failed to delete shopping cart product information","购物车商品信息删除失败!"),
    ;

    private final String message;
    private final String msg;

    BuyCarExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
