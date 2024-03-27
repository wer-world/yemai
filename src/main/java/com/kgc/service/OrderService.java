package com.kgc.service;

import com.kgc.entity.*;

import java.util.List;

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
     * @param user            用户
     * @return 返回消息类, 订单是否创建成功
     */
    Message createOrder(List<OrderDetail> orderDetailList, User user);

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
     * @param pages 分页对象
     * @param order 条件对象
     * @return 返回订单消息类
     */
    Message getOrderList(Pages pages, Order order);

    /**
     * 通过订单id或订单号获取订单相关信息
     *
     * @param order 订单相关信息-(id,serialNumber)
     * @return 返回订单
     */
    Order getOrder(Order order);

    /**
     * 获取所有超时订单
     *
     * @return 返回超时订单列表
     */
    List<Order> getTimeOutOrderList();

    /**
     * 创建手机充值订单
     *
     * @param orderDetail 订单详情
     * @param user        用户对象
     * @return 返回消息类
     */
    Message createMobilePaymentOrder(OrderDetail orderDetail, User user);

    /**
     * 修改订单状态
     * @param order
     * @return
     */
    Message modOrder(Order order);
}
