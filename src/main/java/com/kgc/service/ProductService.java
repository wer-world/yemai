package com.kgc.service;

import com.kgc.entity.Category;
import com.kgc.entity.Product;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 9:13
 * @Description:
 */
public interface ProductService {
    /**
     * 商品id
     * @param id
     * @return
     */
    public Product getProductById(Product product);

    /**
     * 商品id
     * @param product
     * @return
     */
    public List<Product> getSimilarProducts(Product product);

    /**
     * 前端传一级分类的id
     * @param category
     * @return
     */
    public List<Product> getProductsByHigHestId(Category category);
}
