package com.kgc.dao;

import com.kgc.entity.BuyCar;

import java.util.List;

/**
 * 购物车表接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-11:00
 */
public interface BuyCarDao {
    /**
     * 根据用户id获取购物车集合
     *
     * @param userId 用户id
     * @return 返回购物车集合
     */
    List<BuyCar> getBuyCarListByUserId(Integer userId);

    /**
     * 根据id删除购物车商品
     *
     * @param id 购物车id
     * @return 返回影响行数
     */
    Integer delBuyCarProductById(Integer id);
}
