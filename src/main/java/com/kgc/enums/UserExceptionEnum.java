package com.kgc.enums;

import lombok.Getter;

/**
 * 用户异常信息枚举类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-11:42
 */
@Getter
public enum UserExceptionEnum {
    USER_UPDATE_FAILURE("user update failed","用户更新失败!"),
    USER_DELETE_FAILURE("user delete failed","用户删除失败!"),
    UPDATE_PASSWORD_FAILURE("user failed to modify password","用户修改密码失败!"),;

    private final String message;
    private final String msg;

    UserExceptionEnum(String message, String msg) {
        this.message = message;
        this.msg = msg;
    }
}
