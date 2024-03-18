package com.kgc.controller;

import com.kgc.entity.Category;
import com.kgc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("getCategoryList")
    public List<Category> getCategoryList(@RequestBody Category category){
        return categoryService.getCategoryList(category);
    }
}