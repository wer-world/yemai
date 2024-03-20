package com.kgc.dao;

import com.kgc.entity.Alipay;

import java.util.List;

/**
 * 支付宝支付信息返回状态表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-21:35
 */
public interface AlipayDao {
    /**
     * 修改状态为已完成
     *
     * @return 返回影响行数
     */
    int modAlipayStatus(String orderNumber);

    /**
     * 查询未完成的订单状态
     *
     * @return 返回支付宝支付信息列表
     */
    List<Alipay> getAlipayList();

    /**
     * 添加支付信息
     *
     * @param alipay 支付信息
     */
    void addAlipay(Alipay alipay);
}
