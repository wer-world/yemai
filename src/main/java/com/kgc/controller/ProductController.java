package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.entity.Product;
import com.kgc.service.BrandService;
import com.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;

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
    public Message addProduct(@RequestBody Map map,@RequestParam (value = "file",required = false) MultipartFile multipartFile) {
        Object productObj = map.get("products");
        Product product = JSON.parseObject(JSON.toJSONString(productObj),Product.class);
        return productService.addProduct(product,multipartFile);
    }

    @PostMapping("modProduct")
    public Message modProduct(@RequestBody Product product) {
        return productService.modProduct(product);
    }

    @GetMapping("delProduct")
    public Message delProduct(Product product) {
        return productService.delProduct(product);
    }

    @RequestMapping("getProductById")
    public Message getProductById(@RequestBody Product product) {
        return productService.getProductById(product.getId());
    }

    ;

    @RequestMapping("getSimilarProducts")
    public Message getSimilarProducts(@RequestBody Product product) {
        return productService.getSimilarProducts(product);
    }

    ;

    @RequestMapping("getProductsByHigHestId")
    public Message getProductsByHigHestId(@RequestBody Category category) {
        return productService.getProductsByHigHestId(category);
    }

    @RequestMapping("downLoad")
    public void downLoad(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        String picPath = (String) params.get("picPath");
        productService.downLoad(picPath, request, response);
    }




}
