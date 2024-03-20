package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

/**
 *资讯类
 */
@Data
@ToString
public class News {
    private int id;
    private String title;
    private String content;
    private String createTime;
    private int isDelete;
}
