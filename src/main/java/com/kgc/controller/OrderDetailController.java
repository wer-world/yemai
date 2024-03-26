package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.entity.Pages;
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
    public Message getOrderDetailListPage(@RequestBody Map<String, Object> params) {
        String currentPage = (String) params.get("currentPage");
        String pageSize = (String) params.get("pageSize");
        String productName = (String) params.get("productName");
        Pages pages = new Pages();
        OrderDetail orderDetail = new OrderDetail();
        if (currentPage != null && !currentPage.isEmpty()) {
            pages.setCurrentPage(Integer.parseInt(currentPage));
        }
        if (pageSize != null && !pageSize.isEmpty()) {
            pages.setPageSize(Integer.parseInt(pageSize));
        }
        orderDetail.setProductName(productName);
        return orderDetailService.getOrderDetailListPage(pages, orderDetail);
    }
}
