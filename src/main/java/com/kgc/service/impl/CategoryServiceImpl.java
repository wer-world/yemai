package com.kgc.service.impl;

import com.kgc.dao.CategoryDao;
import com.kgc.entity.Category;
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
}
