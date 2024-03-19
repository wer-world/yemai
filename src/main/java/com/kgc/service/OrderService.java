package com.kgc.service;

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
}
