package com.kgc.service;

import com.kgc.entity.Category;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 14:00
 * @Description:
 */
public interface CategoryService {
    List<Category> getCategoryList(Category category);
}
