package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 订单详情信息类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-9:00
 */
@Data
@ToString
public class OrderDetail {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private Double cost;
}
