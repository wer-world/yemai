package com.kgc.service;

import com.kgc.entity.Category;
import com.kgc.entity.Message;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 14:00
 * @Description:
 */
public interface CategoryService {
    /**
     * 查询所有最高级分类
     * 需要传最高级别分类的id，本项目统一为0
     *
     * @param category 分类的id
     * @return 返回分类列表
     */
    List<Category> getCategoryList(Category category);


    /**
     * 查询上级分类的名称
     * 前端传入parentId
     * @param category
     * @return
     */
    Message getParentCategory(Category category);

    /**
     * 根据id查询Category
     * @param category
     * @return
     */
    Message getCategory(Category category);

    /**
     * 添加category
     * @param category
     * @return
     */
    Message addCategory(Category category);

    /**
     * 修改category
     * @param category
     * @return
     */
    Message updateCategory(Category category);

    /**
     * 删除category
     * @param category
     * @return
     */
    Message deleteCategory(Category category);

    /**
     * 查询所有商品所对应的分类
     *
     * @param
     * @return 返回分类列表
     */
    Message getProCategoryNameByType();


}
