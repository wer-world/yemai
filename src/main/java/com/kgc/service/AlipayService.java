package com.kgc.service;

import com.kgc.entity.Alipay;
import com.kgc.entity.Message;
import com.kgc.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 支付宝业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-16:48
 */
public interface AlipayService {
    /**
     * 查询未完成的订单状态
     *
     * @return 返回支付宝支付信息列表
     */
    List<Alipay> getAlipayList();

    /**
     * 统一下单接口
     *
     * @param order 下单的订单
     * @return 返回消息类, 包含支付页面
     */
    String createAlipay(Order order);

    /**
     * 处理支付成功请求业务
     *
     * @param params 支付宝发送的参数
     * @return 返回消息类
     */
    Message notifyUrlAlipay(Map<String, String> params);


    /**
     * 定时器扫描支付宝状态标准异常任务
     *
     * @param params 模拟支付完发送的请求参数
     * @return 返回消息类
     */
    Message replayAlipay(Map<String, String> params);

    /**
     * 支付宝状态表更新
     */
    void fulfilOrderUpdate(String orderNumber);
}
