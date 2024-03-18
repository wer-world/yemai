package com.kgc.entity;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 11:21
 * @Description:
 */
public class Category {
    private Integer id;
    private String name;
    private Integer parentId;
    private String iconClass;
    private Integer isDelete;
    private List<Category> childCategoryList;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public List<Category> getChildCategoryList() {
        return childCategoryList;
    }

    public void setChildCategoryList(List<Category> childCategoryList) {
        this.childCategoryList = childCategoryList;
    }
}
