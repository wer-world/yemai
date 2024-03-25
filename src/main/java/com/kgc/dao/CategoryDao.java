package com.kgc.dao;

import com.kgc.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 11:29
 * @Description:
 */
public interface CategoryDao {
    /**
     * 获取分类列表
     *
     * @param category 分类对象
     * @return 返回分类列表
     */
    List<Category> getCategoryList(Category category);

    Category getParentCategory(Category category);

    Integer addCategory(Category category);

    List<String> getAllCategoryName();

    Category getCategory(Category category);
    Integer updateCategory(Category category);
    Integer deleteCategory(Category category);
    Integer getChildCategoryCount(Category category);

    /**
     * 获取三级分类
     * @return
     */
    List<Category> getProCategoryNameByType();


}
