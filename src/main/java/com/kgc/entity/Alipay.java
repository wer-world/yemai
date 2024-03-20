package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 支付宝状态表实体类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-15:57
 */
@Data
@ToString
public class Alipay {
    private String orderNumber;
    private String params;
    private Integer status;
}
