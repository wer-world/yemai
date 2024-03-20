package com.kgc.service.impl;

import com.kgc.dao.OrderDetailDao;
import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Message delOrderDetailByOrderId(Integer id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        Integer flag = orderDetailDao.delOrderDetail(orderDetail);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }
}
