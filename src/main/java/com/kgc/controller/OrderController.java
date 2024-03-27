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
            return Message.error("购物车没有商品数据!");
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

    @PostMapping("createMobilePaymentOrder")
    public Message createMobilePaymentOrder(@RequestBody Map<String, Object> params) {
        if (params.isEmpty()) {
            return Message.error("未输入充值手机号或充值金额!");
        }
        String mobile = (String) params.get("mobile");
        String costStr = (String) params.get("cost");
        if (mobile == null) {
            return Message.error("未输入充值手机号");
        }
        if (costStr == null) {
            return Message.error("未输入充值金额");
        }
        User user = ThreadLocalUtil.get();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(user.getId());
        orderDetail.setMobile(mobile);
        orderDetail.setCost(Double.parseDouble(costStr));
        return orderService.createMobilePaymentOrder(orderDetail, user);
    }

    @RequestMapping("cancelOrder")
    public Message cancelOrder(Order order) {
        if (order.getId() == null) {
            return Message.error("取消订单,需传入订单id!");
        }
        if (order.getStatus() == null) {
            order.setStatus(2);
        }
        return orderService.cancelOrder(order);
    }

    @PostMapping("getOrderList")
    public Message getOrderList(@RequestBody Map<String, Object> params) {
        String userIdStr = (String) params.get("userId");
        String serialNumber = (String) params.get("serialNumber");
        String loginName = (String) params.get("loginName");
        Pages pages = PagesUtil.parseMapToPages(params);
        Order order = new Order();
        if (userIdStr != null) {
            order.setUserId(Integer.parseInt(userIdStr));
        }
        order.setSerialNumber(serialNumber);
        order.setLoginName(loginName);
        return orderService.getOrderList(pages, order);
    }

    @PostMapping("getUserOrderList")
    public Message getUserOrderList(@RequestBody Map<String, Object> params) {
        Pages pages = PagesUtil.parseMapToPages(params);
        Order order = new Order();
        if (params.get("status")!=null){
            Integer status = Integer.parseInt(params.get("status").toString());
            order.setStatus(status);
        }
        User user = ThreadLocalUtil.get();
        order.setUserId(user.getId());
        return orderService.getOrderList(pages, order);
    }

    @PostMapping("getOrder")
    public Message getOrder(@RequestBody Order order) {
        if (order.getId() == null && order.getSerialNumber() == null) {
            return Message.error("获取订单数据,需传入订单id或订单号!");
        }
        Order resultOrder = orderService.getOrder(order);
        if (resultOrder != null) {
            return Message.success(resultOrder);
        }
        return Message.error();
    }

    @GetMapping("modOrder")
    public Message modOrder(Order order){
        if (order.getId() == null) {
            return Message.error("修改订单状态,需传入订单id！");
        }
        if (order.getStatus()==null){
            return Message.error("修改订单状态,需传入订单状态号！");
        }
        return orderService.modOrder(order);
    }
}
