package com.kgc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.kgc.config.AlipayConfig;
import com.kgc.dao.AlipayDao;
import com.kgc.entity.*;
import com.kgc.enums.AlipayExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.*;
import com.kgc.util.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public List<Alipay> getAlipayList() {
        return alipayDao.getAlipayList();
    }

    @Override
    @Transactional
    public String createAlipay(Order order) {
        Order resultOrder = orderService.getOrder(order);
        if (resultOrder == null) {
            throw new ServiceException("AlipayServiceImpl createAlipay " + AlipayExceptionEnum.ALIPAY_ORDER_NOT_EXIST.getMessage(), AlipayExceptionEnum.ALIPAY_ORDER_NOT_EXIST.getMsg());
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getOpenApi(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", "UTF-8", alipayConfig.getAliPublicKey(), "RSA2");
        AlipayTradePagePayRequest request = getAlipayTradePagePayRequest(resultOrder);
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            String pageRedirectionData = response.getBody();
            if (!response.isSuccess()) {
                throw new ServiceException("AlipayServiceImpl createAlipay " + AlipayExceptionEnum.ALIPAY_CREATE_FAILURE.getMessage(), AlipayExceptionEnum.ALIPAY_CREATE_FAILURE.getMsg());
            }
            return pageRedirectionData;
        } catch (Exception e) {
            throw new ServiceException("AlipayServiceImpl createAlipay " + AlipayExceptionEnum.ALIPAY_CREATE_ERROR.getMessage(), AlipayExceptionEnum.ALIPAY_CREATE_ERROR.getMsg());
        }
    }

    private AlipayTradePagePayRequest getAlipayTradePagePayRequest(Order order) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(alipayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", order.getSerialNumber());
        //支付金额，最小值0.01元
        bizContent.put("total_amount", order.getCost());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", order.getSerialNumber());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        return request;
    }

    @Override
    public Message notifyUrlAlipay(Map<String, String> params) {
        // 完成签证
        try {
            boolean signVerified = AlipaySignature.rsaCheckV2(params, alipayConfig.getAliPublicKey(), "UTF-8", "RSA2"); //调用SDK验证签名
            if (!signVerified) {
                // 验签失败则记录异常日志，并在response中返回failure.
                logger.error("AlipayServiceImpl notifyUrlAlipay " + AlipayExceptionEnum.ALIPAY_CONFIRM_SIGN_FAILURE.getMessage());
                return Message.error(AlipayExceptionEnum.ALIPAY_CONFIRM_SIGN_FAILURE.getMsg());
            }
        } catch (Exception e) {
            logger.error("AlipayServiceImpl notifyUrlAlipay " + AlipayExceptionEnum.ALIPAY_CONFIRM_SIGN_ERROR.getMessage());
            return Message.error(AlipayExceptionEnum.ALIPAY_CONFIRM_SIGN_ERROR.getMsg());
        }

        // 验签通过
        if (!"TRADE_SUCCESS".equals(params.get("trade_status"))) {
            logger.error("AlipayServiceImpl notifyUrlAlipay " + AlipayExceptionEnum.ALIPAY_TRANSACTION_FAILURE.getMessage());
            return Message.error(AlipayExceptionEnum.ALIPAY_TRANSACTION_FAILURE.getMsg());
        }

        String orderNumber = params.get("out_trade_no");
        Alipay alipay = new Alipay();
        alipay.setOrderNumber(orderNumber);
        alipay.setParams(JSON.toJSONString(params));
        Integer flag = alipayDao.addAlipay(alipay);
        if (flag == 0) {
            logger.error("AlipayServiceImpl notifyUrlAlipay " + AlipayExceptionEnum.ALIPAY_ADD_PAYMENT_FAILURE.getMessage());
            return Message.error(AlipayExceptionEnum.ALIPAY_ADD_PAYMENT_FAILURE.getMsg());
        }
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
    @Transactional
    public void fulfilOrderUpdate(String orderNumber) {
        int flag = alipayDao.modAlipayStatus(orderNumber);
        if (flag == 0) {
            throw new ServiceException("AlipayServiceImpl fulfilOrderUpdate " + AlipayExceptionEnum.ALIPAY_TABLE_STATUS.getMessage(), AlipayExceptionEnum.ALIPAY_TABLE_STATUS.getMsg());
        }
    }
}
