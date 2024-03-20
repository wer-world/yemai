package com.kgc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.kgc.config.AlipayConfig;
import com.kgc.dao.AlipayDao;
import com.kgc.entity.Message;
import com.kgc.entity.Order;
import com.kgc.exception.AlipayException;
import com.kgc.service.AlipayService;
import com.kgc.service.OrderService;
import com.kgc.util.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * 支付宝业务实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-16:48
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayDao alipayDao;

    @Autowired
    private OrderService orderService;

    @Override
    public Message createAlipay(Order order) {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getOpenApi(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", "UTF-8", alipayConfig.getAliPublicKey(), "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(alipayConfig.getReturnUrl());

        JSONObject bizContent = new JSONObject();
        String orderNum = UUID.randomUUID().toString();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", orderNum);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", order.getCost());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", orderNum);
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        request.setBizContent(bizContent.toString());
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            // 如果需要返回GET请求，请使用
            // AlipayTradePagePayResponse response = alipayClient.pageExecute(request,"GET");
            String pageRedirectionData = response.getBody();
            if (response.isSuccess()) {
                return Message.success(pageRedirectionData);
            }
            return Message.error("订单创建失败!");
        } catch (Exception e) {
            return Message.error("订单创建错误!");
        }
    }

    @Override
    public Message notifyUrlAlipay(Map<String, String> params) {
        // 完成签证
        try {
            boolean signVerified = AlipaySignature.rsaCheckV2(params, alipayConfig.getAliPublicKey(), "UTF-8", "RSA2"); //调用SDK验证签名
            if (!signVerified) {
                // 验签失败则记录异常日志，并在response中返回failure.
                return Message.error("验签失败!");
            }
        } catch (Exception e) {
            return Message.error("验签错误!");
        }

        // 验签通过
        if (!"TRADE_SUCCESS".equals(params.get("trade_status"))) {
            return Message.error("交易失败!");
        }

        String orderNumber = params.get("out_trade_no");
        // 交易成功通过,开辟子线程
        Thread thread = new Thread(new AlipayUtil(orderService, this, orderNumber));
        // 执行相关业务修改操作
        thread.start();
        // 返回成功
        return Message.success();
    }

    @Override
    public Message replayAlipay(Map<String, String> params) {
        String orderNumber = params.get("out_trade_no");
        // 交易成功通过,开辟子线程
        Thread thread = new Thread(new AlipayUtil(orderService, this, orderNumber));
        // 执行相关业务修改操作
        thread.start();
        // 返回成功
        return Message.success();
    }

    @Override
    public void fulfilOrderUpdate(String orderNumber) {
        int flag = alipayDao.modAlipayStatus(orderNumber);
        if (flag == 0) {
            logger.error("AlipayServiceImpl fulfilOrderUpdate update easybuy_alipay table status error");
            throw new AlipayException();
        }
    }
}
