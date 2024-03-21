package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-18:39
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("cancelOrder")
    public Message cancelOrder(Order order) {
        return orderService.cancelOrder(order);
    }

    @GetMapping("getOrderList")
    public Message getOrderList() {
        return orderService.getOrderList();
    }
}
