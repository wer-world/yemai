package com.kgc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 消息类
 *
 * @Author: 魏小可
 * @Date: 2024-03-16-22:25
 */
@Data
@ToString
@AllArgsConstructor
public class Message {
    private String code;
    private String message;
    private Object data;

    public static Message success() {
        return new Message("200", "操作成功", null);
    }

    public static Message success(Object data) {
        return new Message("200", "操作成功", data);
    }

    public static Message error() {
        return new Message("200", "操作失败", null);
    }

    public static Message error(String message) {
        return new Message("200", message, null);
    }

    public static Message tokenError(String message) {
        return new Message("201", message, null);
    }

    public static Message illegalRequest() {
        return new Message("202", "非法请求,请正常访问!", null);
    }
}
