package com.kgc.service;

import com.kgc.entity.Category;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 14:00
 * @Description:
 */
public interface CategoryService {
    /**
     * 分类列表查询
     *
     * @param category 分类对象
     * @return 返回分类列表
     */
    List<Category> getCategoryList(Category category);
}
