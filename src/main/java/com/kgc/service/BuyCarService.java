package com.kgc.service;

import com.kgc.entity.BuyCar;
import com.kgc.entity.Message;

import java.util.Map;

/**
 * 购物车业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-10:52
 */
public interface BuyCarService {
    /**
     * 根据id删除购物车商品
     *
     * @param id 购物车id
     * @return 返回消息类
     */
    Message delBuyCarProductById(Integer id);

    /**
     * 根据用户id删除购物车商品
     *
     * @return 返回消息类
     */
    Message delBuyCarProductByUserId();

    /**
     * 根据用户id获取购物车集合
     *
     * @param userId 用户id
     * @return 返回消息类
     */
    Message getBuyCarListByUserId(Integer userId);

    /**
     * 添加数据到购物车
     *
     * @param buyCar 需要添加的数据
     * @return 返回消息类
     */
    Message addBuyCar(BuyCar buyCar);

    /**
     * 修改购物车商品数量
     *
     * @param buyCar 条件
     * @return 返回消息类
     */
    Message modBuyCarProductNumById(BuyCar buyCar);
}
