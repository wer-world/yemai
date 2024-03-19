package com.kgc.controller;

import com.kgc.entity.Category;
import com.kgc.entity.Product;
import com.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 9:15
 * @Description:
 */
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("getProductById")
    public Product getProductById(@RequestBody Product product){
        return productService.getProductById(product);
    };

    @RequestMapping("getSimilarProducts")
    public List<Product> getSimilarProducts(@RequestBody Product product){
        return productService.getSimilarProducts(product);
    };

    @RequestMapping("getProductsByHigHestId")
    public List<Product> getProductsByHigHestId(@RequestBody Category category){
        return productService.getProductsByHigHestId(category);
    }
}
