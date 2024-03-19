package com.kgc;

import com.kgc.dao.ProductDao;
import com.kgc.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: 魏小可
 * @Date: 2024-03-19-15:55
 */
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void getProductList(){
        List<Product> productList = productDao.getProductList();
        System.out.println(productList);
    }
}
