package com.kgc.util;

import com.kgc.service.AlipayService;
import com.kgc.service.OrderDetailService;
import com.kgc.service.OrderService;
import lombok.AllArgsConstructor;

/**
 * 支付宝工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-17:37
 */
@AllArgsConstructor
public class AlipayUtil implements Runnable {

    private OrderService orderService;

    private AlipayService alipayService;

    private String orderNumber;

    @Override
    public void run() {
        // 1.更新订单状态,修改相关信息
        orderService.fulfilOrderPay(orderNumber);
        // 2.修改支付宝状态接口信息
        alipayService.fulfilOrderUpdate(orderNumber);
        // 3.编写通知
    }
}
