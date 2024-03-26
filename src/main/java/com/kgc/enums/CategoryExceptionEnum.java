package com.kgc.enums;

import lombok.Getter;

/**
 * 分类异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-16:22
 */
@Getter
public enum CategoryExceptionEnum {
    CATEGORY_ADD_FAILURE("adding classification information failed", "分类信息添加失败!"),
    CATEGORY_UPDATE_FAILURE("failed to update classification information", "分类信息更新失败!"),
    CATEGORY_DELETE_FAILURE("failed to delete classification information", "分类信息删除失败!"),
    CATEGORY_ALREADY_EXISTS("this category name already exists", "该类别名已存在!"),
    CATEGORY_SUBCLASSES_EXISTS("there are subcategories under this category, and cannot be deleted", "该分类下有子分类，不能删除!"),
    CATEGORY_PRODUCT_EXISTS("there are commodities under this category, and cannot be deleted", "该分类下有商品，不能删除!"),
    ;


    private final String message;
    private final String msg;

    CategoryExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
