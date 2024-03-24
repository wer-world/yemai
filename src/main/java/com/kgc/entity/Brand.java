package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 品牌表实体类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-10:55
 */
@Data
@ToString
public class Brand {
    private Integer id;
    private String name;
    private Date createTime;
}
