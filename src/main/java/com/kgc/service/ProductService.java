package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.Category;
import com.kgc.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 初始化数据到ES中
     *
     * @return 返回初始化是否成功
     */
    Boolean saveProductListToEs();

    /**
     * 分页查询商品
     * {
     *     "currentPage": 1, // 页码
     *     "pageSize": 5, // 页面容量
     *     "minPrice": 0.0, // 最小价格
     *     "maxPrice": 300.0, // 最大价格
     *     "name": "", // 商品名称
     *     "brandName": "", // 商品品牌
     *     "categoryName": "", // 分类美名称
     *     "isSales": null, // 是否按销量排序 true-倒序 false-正序 null不排序
     *     "isPrice": true, // 是否按价格排序 true-倒序 false-正序 null不排序
     *     "isNewProduct": null // 是否按新品排序 true-倒序 false-正序 null不排序
     * }
     *
     * @param paramMap 查询条件(currentPage, pageSize, mixPrice, maxPrice,name,brandName,categoryName,isSale,isPrice,isNewProducts)
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
    Message addProduct(Product product, MultipartFile multipartFile);

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
     * 通过商品id查询商品
     *
     * @param id 商品id
     * @return 返回对应商品
     */
    Product getProductById(Integer id);

    /**
     * 根据商品id获取同类商品
     *
     * @param product 商品id
     * @return 返回商品列表消息类
     */
    Message getSimilarProducts(Product product);

    /**
     * 通过一级id获取下面所有商品
     *
     * @param category 前端传一级分类的id
     * @return 返回商品列表消息类
     */
    Message getProductsByHigHestId(Category category);

    /**
     * 下载图片
     * @param request
     * @param response
     */
    void downLoad(String picPath, HttpServletRequest request, HttpServletResponse response);




}
