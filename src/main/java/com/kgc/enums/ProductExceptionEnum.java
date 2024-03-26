package com.kgc.enums;

import lombok.Getter;

/**
 * 商品异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-10:15
 */
@Getter
public enum ProductExceptionEnum {
    PRODUCT_UPDATE_FAILURE("failed to update commodity information","商品信息更新失败!"),
    PRODUCT_DELETE_FAILURE("failed to delete commodity information","商品信息删除失败!"),
    ;



    private final String message;
    private final String msg;

    ProductExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
