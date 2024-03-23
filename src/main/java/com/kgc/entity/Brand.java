package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
public class Brand {
    private int id;
    private String name;
    private Date createTime;
    private int isDelete;

}
