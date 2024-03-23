package com.kgc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
public class Address {
    private int id;
    private int userId;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int isDefault;
    private String remark;
    private int isDelete;
}
