package com.kgc.dao;

import com.kgc.entity.Order;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取所有订单
     *
     * @return 返回订单列表
     */
    List<Order> getOrderList(Map<String, Object> params);

    /**
     * 根据用户id获取他的所有订单
     *
     * @return 返回订单列表
     */
    List<Order> getOrderListByIdCondition(Map<String, Object> params);

    /**
     * 根据用户id获取他的所有订单
     *
     * @return 返回订单列表
     */
    Integer getOrderListCount(Map<String, Object> params);

    /**
     * 获取订单数据
     *
     * @param order 条件-(id,serialNumber)
     * @return 返回订单
     */
    Order getOrder(Order order);

    /**
     * 获取所有超时订单
     *
     * @return 返回超时订单列表
     */
    List<Order> getTimeOutOrderList();
}
