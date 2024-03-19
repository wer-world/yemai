package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.Product;
import com.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商品管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-9:30
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("getProductListPages")
    public Message getProductListPages(@RequestBody Map<String, Object> paramMap) {
        return productService.getProductListPages(paramMap);
    }

    @GetMapping("getProduct")
    public Message getProduct(Product product) {
        return productService.getProduct(product);
    }

    @PostMapping("addProduct")
    public Message addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping("modProduct")
    public Message modProduct(@RequestBody Product product) {
        return productService.modProduct(product);
    }

    @GetMapping("delProduct")
    public Message delProduct(Product product){
        return productService.delProduct(product);
    }

}
