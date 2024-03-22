package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 订单实体类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-22:17
 */
@Data
@ToString
public class Order {
    private Integer id;
    private Integer userId;
    private String loginName;
    private String userAddress;
    private Date createTime;
    private Double cost;
    private String serialNumber;
    private Integer status;
    private String statusName;
    private String mobile;
}
