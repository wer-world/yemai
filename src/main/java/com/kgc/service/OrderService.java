package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.entity.OrderDetail;

import java.util.List;
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
     * @param orderDetailList 购买的商品集合
     * @return 返回消息类, 订单是否创建成功
     */
    Message createOrder(List<OrderDetail> orderDetailList);

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
    Message getOrderList(Map<String, Object> params);

    /**
     * 通过订单id获取订单相关信息
     *
     * @param order 订单相关信息-(id,serialNumber)
     * @return 返回消息类
     */
    Message getOrder(Order order);
}
