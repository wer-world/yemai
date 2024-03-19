package com.kgc.entity;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 8:30
 * @Description:
 */
public class Product {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private Integer stock;
    private Integer categoryLevelId;
    private Integer picId;
    private Integer isDelete;

    private List<File> fileList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryLevelId() {
        return categoryLevelId;
    }

    public void setCategoryLevelId(Integer categoryLevelId) {
        this.categoryLevelId = categoryLevelId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
