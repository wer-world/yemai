package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

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
    private Integer createTime;
    private Integer isDelete;

}
