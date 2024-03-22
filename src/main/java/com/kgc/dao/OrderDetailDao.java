package com.kgc.dao;

import com.kgc.entity.OrderDetail;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-14:42
 */
public interface OrderDetailDao {
    /**
     * 根据条件获取订单详情列表
     *
     * @param orderDetail 查询条件(id, orderId)
     * @return 返回订单详情列表
     */
    List<OrderDetail> getOrderDetailList(OrderDetail orderDetail);

    /**
     * 根据条件删除订单详情
     *
     * @param orderDetail 删除条件(id, orderId)
     * @return 返回影响行数
     */
    Integer delOrderDetail(OrderDetail orderDetail);

    /**
     * 获取订单详情列表信息
     *
     * @param params 参数
     * @return 返回消息类
     */
    List<OrderDetail> getOrderDetailListPage(Map<String, Object> params);
}
