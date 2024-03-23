package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 订单详情列表管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-9:46
 */
@RestController
@RequestMapping("orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("getOrderDetailListPage")
    public Message getOrderDetailListPage(@RequestBody Map<String, Object> params){
        return orderDetailService.getOrderDetailListPage(params);
    }
}
