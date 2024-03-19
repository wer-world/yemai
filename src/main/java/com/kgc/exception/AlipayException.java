package com.kgc.exception;

import lombok.Getter;

/**
 * 支付宝相关异常
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-21:39
 */
@Getter
public class AlipayException extends RuntimeException {
    private String msg;

    public AlipayException() {
    }

    public AlipayException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
