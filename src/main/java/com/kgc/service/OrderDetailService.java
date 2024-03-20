package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;

import java.util.List;

/**
 * 订单详情业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-9:37
 */
public interface OrderDetailService {
    /**
     * 根据订单id获取订单详情列表
     *
     * @param id 订单id
     * @return 返回订单详情列表
     */
    List<OrderDetail> getOrderDetailListByOrderId(Integer id);

    /**
     * 根据订单id删除订单详情信息
     *
     * @param id 订单id
     * @return 返回信息类(200删除成功)
     */
    Message delOrderDetailByOrderId(Integer id);
}
