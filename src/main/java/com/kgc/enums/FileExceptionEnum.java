package com.kgc.enums;

import lombok.Getter;

/**
 * 文件信息异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-11:23
 */
@Getter
public enum FileExceptionEnum {
    FILE_ADD_FAILURE("file addition failed","文件添加失败!"),
    FILE_UPDATE_FAILURE("file update failed","文件更新失败!");

    private final String message;
    private final String msg;

    FileExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
