package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 购物车实体类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-10:53
 */
@Data
@ToString
public class BuyCar {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productName;
    private String description;
    private Double productPrice;
    private String picPath;
    private Integer productNum;
    private Date createTime;

    public void setPicPath(String picPath) {
        if (picPath != null && !picPath.isEmpty()) {
            try {
                this.picPath = URLEncoder.encode(picPath,"utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
