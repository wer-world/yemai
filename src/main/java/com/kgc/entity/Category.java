package com.kgc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 11:21
 * @Description:
 */
@Data
@ToString
public class Category {
    private Integer id;
    private String name;
    private Integer parentId;
    private String iconClass;
    private Integer isDelete;
    private List<Category> childCategoryList;
}
