package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kgc.entity.*;
import com.kgc.service.OrderService;
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
        return orderService.createOrder(orderDetailList);
    }

    @RequestMapping("cancelOrder")
    public Message cancelOrder(Order order) {
        return orderService.cancelOrder(order);
    }

    @PostMapping("getOrderList")
    public Message getOrderList(@RequestBody Map<String, Object> params) {
        User user = ThreadLocalUtil.get();
        String currentPageStr = params.get("currentPage").toString();
        String pageSizeStr = params.get("pageSize").toString();
        String serialNumber = (String) params.get("serialNumber");
        int currentPage = 1;
        int pageSize = 5;
        if (currentPageStr != null && !currentPageStr.isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Pages pages = new Pages();
        Order order = new Order();
        pages.setCurrentPage(currentPage);
        pages.setPageSize(pageSize);
        order.setUserId(user.getId());
        order.setSerialNumber(serialNumber);
        return orderService.getOrderList(pages,order);
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
