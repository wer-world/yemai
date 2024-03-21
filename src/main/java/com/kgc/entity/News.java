package com.kgc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *资讯类
 */
@Data
@ToString
public class News {
    private int id;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int isDelete;
}
