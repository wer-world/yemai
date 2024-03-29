package com.kgc.service.impl;

import com.kgc.dao.CategoryDao;
import com.kgc.dao.ProductDao;
import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.enums.CategoryExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            for (Category c2 : categoryList2) {
                tempCategory.setParentId(c2.getId());
                List<Category> categoryList3 = categoryDao.getCategoryList(tempCategory);
                c2.setChildCategoryList(categoryList3);
            }
        }
        return categoryList1;
    }

    @Override
    public Message getProCategoryNameByType() {
        List<Category> proCategoryNameByType = categoryDao.getProCategoryNameByType();
        return Message.success(proCategoryNameByType);
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
    @Transactional
    public Message addCategory(Category category) {
        List<String> categoryNameList = categoryDao.getAllCategoryName();
        for (String name : categoryNameList) {
            if (name.equals(category.getName())) {
                throw new ServiceException("CategoryServiceImpl addCategory " + CategoryExceptionEnum.CATEGORY_ALREADY_EXISTS.getMessage(), CategoryExceptionEnum.CATEGORY_ALREADY_EXISTS.getMsg());
            }
        }
        Integer affectRow = -1;
        affectRow = categoryDao.addCategory(category);
        if (affectRow < 1) {
            throw new ServiceException("CategoryServiceImpl addCategory " + CategoryExceptionEnum.CATEGORY_ADD_FAILURE.getMessage(), CategoryExceptionEnum.CATEGORY_ADD_FAILURE.getMsg());
        }
        return Message.success("新增成功!");
    }

    @Override
    @Transactional
    public Message updateCategory(Category category) {
        List<String> categoryNameList = categoryDao.getAllCategoryName();
        int count = 0;
        for (String name : categoryNameList) {
            if (name.equals(category.getName())) {
                throw new ServiceException("CategoryServiceImpl updateCategory " + CategoryExceptionEnum.CATEGORY_ALREADY_EXISTS.getMessage(), CategoryExceptionEnum.CATEGORY_ALREADY_EXISTS.getMsg());
            }
        }
        Integer affectRow = -1;
        affectRow = categoryDao.updateCategory(category);
        if (affectRow < 1) {
            throw new ServiceException("CategoryServiceImpl updateCategory " + CategoryExceptionEnum.CATEGORY_UPDATE_FAILURE.getMessage(), CategoryExceptionEnum.CATEGORY_UPDATE_FAILURE.getMsg());
        }
        return Message.success("修改成功!");
    }

    @Override
    @Transactional
    public Message deleteCategory(Category category) {
        int count = categoryDao.getChildCategoryCount(category);
        if (count > 0) {
            throw new ServiceException("CategoryServiceImpl deleteCategory " + CategoryExceptionEnum.CATEGORY_SUBCLASSES_EXISTS.getMessage(), CategoryExceptionEnum.CATEGORY_SUBCLASSES_EXISTS.getMsg());
        }
        count = productDao.getExistParentCategoryProductCount(category);
        if (count > 0) {
            throw new ServiceException("CategoryServiceImpl deleteCategory " + CategoryExceptionEnum.CATEGORY_PRODUCT_EXISTS.getMessage(), CategoryExceptionEnum.CATEGORY_PRODUCT_EXISTS.getMsg());
        }
        count = categoryDao.deleteCategory(category);
        if (count < 1) {
            throw new ServiceException("CategoryServiceImpl deleteCategory " + CategoryExceptionEnum.CATEGORY_DELETE_FAILURE.getMessage(), CategoryExceptionEnum.CATEGORY_DELETE_FAILURE.getMsg());
        }
        return Message.success("删除成功!");
    }
}
