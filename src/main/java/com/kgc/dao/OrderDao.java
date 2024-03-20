package com.kgc.dao;

import com.kgc.entity.Order;

/**
 * 订单表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-22:00
 */
public interface OrderDao {
    /**
     * 订单支付完成对应信息修改
     *
     * @param orderNumber 订单号
     * @return 返回影响行数
     */
    Integer fulfilOrderPay(String orderNumber);

    /**
     * 添加订单
     *
     * @param order 需要添加的订单信息
     * @return 返回影响行数
     */
    Integer addOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 修改条件(id),修改信息
     * @return 返回影响行数
     */
    Integer modOrder(Order order);

    /**
     * 取消订单
     *
     * @param order 取消订单的条件id
     * @return 返回影响行数
     */
    Integer cancelOrder(Order order);
}
