package com.kgc.dao;

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
}
