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
    public List<Category> getCategoryList(Category category);
}
