package com.kgc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kgc.dao.OrderDetailDao;
import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.entity.OrderDetail;
import com.kgc.entity.Pages;
import com.kgc.enums.OrderDetailExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情业务接口实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-14:29
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public List<OrderDetail> getOrderDetailListByOrderId(Integer id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailList(orderDetail);
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            return orderDetailList;
        }
        return null;
    }

    public Message getOrderDetailList(Integer id){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailList(orderDetail);
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            return Message.success(orderDetailList);
        }
        return Message.error();
    }

    @Override
    public List<OrderDetail> getOrderDetailMobileListByOrderId(Integer id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailMobileList(orderDetail);
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            return orderDetailList;
        }
        return null;
    }


    @Override
    @Transactional
    public Message delOrderDetailByOrderId(Integer id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        Integer flag = orderDetailDao.delOrderDetail(orderDetail);
        if (flag == 0) {
            throw new ServiceException("OrderDetailServiceImpl delOrderDetailByOrderId " + OrderDetailExceptionEnum.ORDER_DETAIL_DELETE_FAILURE.getMessage(), OrderDetailExceptionEnum.ORDER_DETAIL_DELETE_FAILURE.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message getOrderDetailListPage(Pages pages, OrderDetail orderDetail) {
        Page<Object> page = PageHelper.startPage(pages.getCurrentPage(), pages.getPageSize());
        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailListPage(orderDetail);
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderDetailList", orderDetailList);
            resultMap.put("totalCount", page.getTotal());
            return Message.success(resultMap);
        }
        return Message.error();
    }

    @Override
    @Transactional
    public Message addOrderDetail(OrderDetail orderDetail) {
        Integer flag = orderDetailDao.addOrderDetail(orderDetail);
        if (flag == 0) {
            throw new ServiceException("OrderDetailServiceImpl delOrderDetailByOrderId " + OrderDetailExceptionEnum.ORDER_DETAIL_ADD_FAILURE.getMessage(), OrderDetailExceptionEnum.ORDER_DETAIL_ADD_FAILURE.getMsg());
        }
        return Message.success();
    }
}
