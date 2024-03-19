package com.kgc.dao;

import com.kgc.entity.Category;
import com.kgc.entity.Product;

import java.util.List;

/**
 * 商品表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-10:12
 */
public interface ProductDao {
    /**
     * 查询所有商品信息
     *
     * @return 返回商品信息列表
     */
    List<Product> getProductList();

    /**
     * 添加商品
     *
     * @param product 需要添加的商品信息
     * @return 返回影响条数
     */
    Integer addProduct(Product product);


    /**
     * 修改商品
     *
     * @param product 需要修改的商品
     * @return 返回影响条数
     */
    Integer modProduct(Product product);

    /**
     * 删除商品
     *
     * @param product 需要删除的商品(id)
     * @return 返回影响条数
     */
    Integer delProduct(Product product);

    /**
     * 通过条件查询商品
     *
     * @param product 查询条件(id)
     * @return
     */
    Product getProduct(Product product);

    Product getProductById(Product product);

    List<Product> getSimilarProducts(Product product);

    List<Product> getProductsByHigHestId(Category category);
}
