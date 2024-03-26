package com.kgc.enums;

import lombok.Getter;

/**
 * 收藏异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-16:22
 */
@Getter
public enum CollectionExceptionEnum {
    COLLECTION_ADD_FAILURE("adding classification information failed", "添加收藏失败!"),
    COLLECTION_DELETE_FAILURE("adding classification information failed", "删除失败!"),
    PRODUCT_EXIST_ERROR("adding classification information failed", "该商品已在收藏夹，收藏失败!"),
    ;


    private final String message;
    private final String msg;

    CollectionExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
