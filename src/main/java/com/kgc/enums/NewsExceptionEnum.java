package com.kgc.enums;

import lombok.Getter;

/**
 * 新闻异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-16:22
 */
@Getter
public enum NewsExceptionEnum {
    NEWS_ADD_FAILURE("news add failed", "新闻添加失败!"),
    NEWS_UPDATE_FAILURE("news update failed", "新闻更新失败!"),
    NEWS_DELETE_FAILURE("news deletion failed", "新闻删除失败!"),;


    private final String message;
    private final String msg;

    NewsExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
