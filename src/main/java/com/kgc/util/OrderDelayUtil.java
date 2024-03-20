package com.kgc.util;

import com.kgc.entity.Order;
import com.kgc.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单延迟处理工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-20:56
 */
@Component
@RabbitListener(queues = "orderDelayKey")
public class OrderDelayUtil {
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(String orderId) {
        // 取消订单
        Order order = new Order();
        order.setId(Integer.parseInt(orderId));
        orderService.cancelOrder(order);
    }
}
