package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.entity.OrderDetail;
import com.kgc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("createOrder")
    public Message createOrder(@RequestBody Map<String, Object> buyCar) {
        if (buyCar.isEmpty()) {
            return Message.error();
        }
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<Object> buyCarList = (List<Object>) buyCar.get("buyCar");
        for (Object o : buyCarList) {
            OrderDetail orderDetail = JSON.parseObject(JSON.toJSONString(o), new TypeReference<OrderDetail>() {
            });
            orderDetailList.add(orderDetail);
        }
        return orderService.createOrder(orderDetailList);
    }

    @RequestMapping("cancelOrder")
    public Message cancelOrder(Order order) {
        return orderService.cancelOrder(order);
    }

    @PostMapping("getOrderList")
    public Message getOrderList(@RequestBody Map<String, Object> params) {
        return orderService.getOrderList(params);
    }

    @PostMapping("getOrder")
    public Message getOrder(@RequestBody Order order) {
        return orderService.getOrder(order);
    }
}
