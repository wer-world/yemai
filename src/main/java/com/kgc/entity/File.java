package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 文件类
 * @Author:25378
 * @DATE:2024/3/19 8:31
 * @Description:
 */
@Data
@ToString
public class File {
    private Integer id;
    private String picPath;
    private Integer product_id;
    private Integer user_id;
    private Date createTime;
    private Integer isDelete;
}
