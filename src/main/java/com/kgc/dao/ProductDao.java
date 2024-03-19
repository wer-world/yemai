package com.kgc.dao;

import com.kgc.entity.Category;
import com.kgc.entity.Product;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 8:58
 * @Description:
 */
public interface ProductDao {
    public Product getProductById(Product product);

    public List<Product> getSimilarProducts(Product product);

    public List<Product> getProductsByHigHestId(Category category);
}
