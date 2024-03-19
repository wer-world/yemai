package com.kgc.service.impl;

import com.kgc.dao.ProductDao;
import com.kgc.entity.Category;
import com.kgc.entity.Product;
import com.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 9:13
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Product product) {
        return productDao.getProductById(product);
    }

    @Override
    public List<Product> getSimilarProducts(Product product) {
        Product product1 = getProductById(product);
        return productDao.getSimilarProducts(product1);
    }

    @Override
    public List<Product> getProductsByHigHestId(Category category) {
        return productDao.getProductsByHigHestId(category);
    }
}
