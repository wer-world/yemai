package com.kgc.constant;

/**
 * 易买网常量类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-23:03
 */
public class EasyBuyConstant {
    public static final String TOKEN_SIGN = "EasyBuyNet"; // 令牌签名
    public static final int TOKEN_OVER_HOURS = 1; // 令牌超时时间(单位小时)
    public static final Long TOKEN_OVER_TIME = TOKEN_OVER_HOURS * 60 * 1000L; // 令牌超时时间(单位毫秒)
}
