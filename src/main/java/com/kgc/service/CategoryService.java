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
     * 需要传最高级别分类的id，本项目统一为0
     * @param category 分类的id
     * @return 返回分类列表
     */
    public List<Category> getCategoryList(Category category);
}
