package com.kgc.controller;

import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/18 14:08
 * @Description:
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("getCategoryList")
    public Message getCategoryList(@RequestBody Category category) {
        List<Category> categoryList = categoryService.getCategoryList(category);
        if (categoryList != null) {
            return Message.success(categoryList);
        }
        return Message.error();
    }

    @RequestMapping("getParentCategory")
    Message getParentCategory(@RequestBody Category category){
        return categoryService.getParentCategory(category);
    }

    @RequestMapping("addCategory")
    public Message addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @RequestMapping("updateCategory")
    public Message updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }
    @RequestMapping("deleteCategory")
    public Message deleteCategory(@RequestBody Category category){
        return categoryService.deleteCategory(category);
    }

    @RequestMapping("getCategory")
    Message getCategory(@RequestBody Category category){
        return categoryService.getCategory(category);
    }

    @RequestMapping("getProCategoryNameByType")
    public Message getProCategoryNameByType() {
        return categoryService.getProCategoryNameByType();
    }

}
