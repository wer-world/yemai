package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:01
 * @Description:
 */
@Data
@ToString
public class Collections {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String description;
    private String picPath;
    private double price;
    private Date createTime;
    private Integer isDelete;
}
