package com.kgc.service.impl;

import com.kgc.dao.CategoryDao;
import com.kgc.dao.ProductDao;
import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 14:00
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Category> getCategoryList(Category category) {
        Category tempCategory = new Category();
        List<Category> categoryList1 = categoryDao.getCategoryList(category);
        for (Category c1 : categoryList1) {
            tempCategory.setParentId(c1.getId());
            List<Category> categoryList2 = categoryDao.getCategoryList(tempCategory);
            c1.setChildCategoryList(categoryList2);
            for (Category c2 : categoryList2){
                tempCategory.setParentId(c2.getId());
                List<Category> categoryList3 = categoryDao.getCategoryList(tempCategory);
                c2.setChildCategoryList(categoryList3);
            }
        }
        return categoryList1;
    }

    @Override
    public Message getParentCategory(Category category) {
        Category category1 = categoryDao.getParentCategory(category);
        return Message.success(category1);
    }

    @Override
    public Message getCategory(Category category) {
        Category category1 = categoryDao.getCategory(category);
        return Message.success(category1);
    }

    @Override
    public Message addCategory(Category category) {
        List<String> categoryNameList = categoryDao.getAllCategoryName();
        for (String name:categoryNameList){
            if (name.equals(category.getName())){
                return Message.error("该类别名已存在!");
            }
        }
        Integer affectRow = -1;
        affectRow = categoryDao.addCategory(category);
        if (affectRow<1){
            return Message.error("新增失败!");
        }
        return Message.success("新增成功!");
    }

    @Override
    public Message updateCategory(Category category) {
        List<String> categoryNameList = categoryDao.getAllCategoryName();
        int count = 0;
        for (String name:categoryNameList){
            if (name.equals(category.getName())){
                return Message.error("该类别名已存在!");
            }
        }
        Integer affectRow = -1;
        affectRow = categoryDao.updateCategory(category);
        if (affectRow<1){
            return Message.error("修改失败!");
        }
        return Message.success("修改成功!");
    }

    @Override
    public Message deleteCategory(Category category) {
        int count = categoryDao.getChildCategoryCount(category);
        if (count>0){
            return Message.error("该分类下有子分类，不能删除！");
        }
        count = productDao.getExistParentCategoryProductCount(category);
        if (count>0){
            return Message.error("该分类下有商品，不能删除！");
        }
        count = categoryDao.deleteCategory(category);
        if (count<1){
            return Message.error("删除失败!");
        }
        return Message.success("删除成功!");
    }
}
