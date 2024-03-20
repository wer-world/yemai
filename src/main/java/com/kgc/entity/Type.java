package com.kgc.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author:25378
 * @DATE:2024/3/20 20:03
 * @Description:
 */
@Data
public class Type {
    private Integer id;
    private String name;
    private Date createTime;
    private Integer isDelete;
}
