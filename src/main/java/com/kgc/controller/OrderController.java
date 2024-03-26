package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kgc.entity.*;
import com.kgc.service.OrderService;
import com.kgc.util.PagesUtil;
import com.kgc.util.ThreadLocalUtil;
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
        User user = ThreadLocalUtil.get();
        return orderService.createOrder(orderDetailList, user);
    }

    @RequestMapping("cancelOrder")
    public Message cancelOrder(Order order) {
        return orderService.cancelOrder(order);
    }

    @PostMapping("getOrderList")
    public Message getOrderList(@RequestBody Map<String, Object> params) {
        String userIdStr = (String) params.get("userId");
        String serialNumber = (String) params.get("serialNumber");
        Pages pages = PagesUtil.parseMapToPages(params);
        Order order = new Order();
        if (userIdStr != null) {
            order.setUserId(Integer.parseInt(userIdStr));
        }
        order.setSerialNumber(serialNumber);
        return orderService.getOrderList(pages, order);
    }

    @GetMapping("getOrderList")
    public Message getUserOrderList(Pages pages) {
        return orderService.getOrderList(pages, new Order());
    }

    @PostMapping("getOrder")
    public Message getOrder(@RequestBody Order order) {
        Order resultOrder = orderService.getOrder(order);
        if (resultOrder != null) {
            return Message.success(resultOrder);
        }
        return Message.error();
    }
}
