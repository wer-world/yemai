package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.Category;
import com.kgc.entity.Product;

import java.util.Map;
import java.util.List;

/**
 * 商品管理业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-9:50
 */
public interface ProductService {

    /**
     * 是否初始化数据到ES中
     *
     * @return true-初始化 false-不初始化
     */
    Boolean isNotInitEs();

    /**
     * 初始化数据到ES中
     *
     * @return 返回初始化是否成功
     */
    Boolean saveProductListToEs();

    /**
     * 分页查询商品
     *
     * @param paramMap 查询条件(currentPage, pageSize, mixPrice, maxPrice, categoryName)
     * @return 返回消息类(携带分页对象, 分页数据, 数据类型Map ( productList, page))
     */
    Message getProductListPages(Map<String, Object> paramMap);

    /**
     * 获取商品信息
     *
     * @param product 需要查询的条件
     * @return 返回携带商品信息的消息类
     */
    Message getProduct(Product product);

    /**
     * 添加商品
     *
     * @param product 需要添加的商品信息
     * @return 返回消息类
     */
    Message addProduct(Product product);

    /**
     * 修改商品
     *
     * @param product 需要修改的商品信息
     * @return 返回消息类
     */
    Message modProduct(Product product);

    /**
     * 删除商品
     *
     * @param product 需要删除的商品(id)
     * @return 返回消息类
     */
    Message delProduct(Product product);

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
