package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.entity.Pages;

import java.util.List;
import java.util.Map;

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
     * 获取手机订单详情列表
     *
     * @param id 手机订单id
     * @return 返回订单列表
     */
    List<OrderDetail> getOrderDetailMobileListByOrderId(Integer id);

    /**
     * 查询订单详情列表
     * @param id
     * @return
     */
    public Message getOrderDetailList(Integer id);

    /**
     * 根据订单id删除订单详情信息
     *
     * @param id 订单id
     * @return 返回信息类(200删除成功)
     */
    Message delOrderDetailByOrderId(Integer id);

    /**
     * 获取订单详情列表信息
     *
     * @param pages       分页对象
     * @param orderDetail 条件对象
     * @return 返回消息类
     */
    Message getOrderDetailListPage(Pages pages, OrderDetail orderDetail);

    /**
     * 添加订单详情信息
     *
     * @param orderDetail 订单详情信息
     * @return 返回是否添加成功
     */
    Message addOrderDetail(OrderDetail orderDetail);
}
