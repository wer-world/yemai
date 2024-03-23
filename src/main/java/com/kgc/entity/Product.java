package com.kgc.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 商品类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-9:16
 */
@Data
@ToString
@Document(indexName = "product")
public class Product {
    @Id
    private Integer id;
    @Transient
    private Integer brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Integer)
    private Integer stock;
    @Transient
    private Integer categoryLevelId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String categoryName;
    @Transient
    private Integer picId;
    @Field(type = FieldType.Keyword)
    private String picPath;
    @Field(type = FieldType.Integer)
    private Integer sales;
    @Field(type = FieldType.Boolean)
    private Boolean newProduct;

    public void setPicPath(String picPath) {
        if (picPath != null && !"".equals(picPath)) {
            try {
                this.picPath = URLEncoder.encode(picPath,"utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
