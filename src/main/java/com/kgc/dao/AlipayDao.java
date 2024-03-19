package com.kgc.dao;

/**
 * 支付宝支付信息返回状态表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-21:35
 */
public interface AlipayDao {
    /**
     * 修改状态为已支付
     *
     * @return 返回影响行数
     */
    int modAlipayStatus(String orderNumber);
}
