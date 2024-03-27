package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.entity.Pages;
import com.kgc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Message getOrderDetailListPage(@RequestBody Map<String, Object> params) {
        params.putIfAbsent("currentPage", 1); // 当前页码
        params.putIfAbsent("pageSize", 5); // 分页容量
        String orderIdStr = (String) params.get("orderId");
        if (orderIdStr==null || orderIdStr.isEmpty()){
            return Message.error("请传入orderId！");
        }
        Integer orderId = Integer.valueOf(orderIdStr);
        String productName = (String) params.get("productName");
        Pages pages = new Pages();
        OrderDetail orderDetail = new OrderDetail();
        pages.setCurrentPage(Integer.parseInt(params.get("currentPage").toString()));
        pages.setPageSize(Integer.parseInt(params.get("pageSize").toString()));
        orderDetail.setOrderId(orderId);
        orderDetail.setProductName(productName);
        return orderDetailService.getOrderDetailListPage(pages, orderDetail);
    }

    @GetMapping("getOrderDetailList")
    public Message getOrderDetailList(Integer id){
        return orderDetailService.getOrderDetailList(id);
    }
}
