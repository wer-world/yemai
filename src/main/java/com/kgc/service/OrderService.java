package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.entity.OrderDetail;

import java.util.Map;

/**
 * 订单业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-18:39
 */
public interface OrderService {

    /**
     * 完成订单支付，订单信息进行修改
     */
    void fulfilOrderPay(String orderNumber);

    /**
     * 创建订单
     *
     * @param orderDetailMap 创建的订单
     * @return 返回消息类, 订单是否创建成功
     */
    Message createOrder(Map<Integer, OrderDetail> orderDetailMap);

    /**
     * 添加订单信息
     *
     * @param order 需要添加的订单信息
     * @return 更新信息
     */
    Message addOrder(Order order);

    /**
     * 更新订单信息
     *
     * @param order 需要更新的订单信息
     * @return 返回更新消息类
     */
    Message modOrder(Order order);

    /**
     * 取消订单
     *
     * @param order 需要取消的订单号
     * @return 返回取消的消息类
     */
    Message cancelOrder(Order order);

    /**
     * 获取所有订单
     *
     * @return 返回订单消息类
     */
    Message getOrderList();
}
