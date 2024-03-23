package com.kgc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kgc.dao.OrderDetailDao;
import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Message getOrderDetailListPage(Map<String, Object> params) {
        Integer currentPage = (Integer) params.get("currentPage");
        Integer pageSize = (Integer) params.get("pageSize");
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailListPage(params);
        if (orderDetailList != null && !orderDetailList.isEmpty()){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderDetailList", orderDetailList);
            resultMap.put("totalCount", page.getTotal());
            return Message.success(resultMap);
        }
        return Message.error();
    }
}
