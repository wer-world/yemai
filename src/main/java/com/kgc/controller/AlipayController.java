package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.OrderDetail;
import com.kgc.enums.AlipayExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-16:47
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @PostMapping("createAlipay")
    public void createAlipay(@RequestBody Map<Integer, OrderDetail> orderDetailMap, HttpServletResponse response) throws IOException {
        Message message = alipayService.createAlipay(orderDetailMap);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if ("200".equals(message.getCode())) {
            writer.print(message.getData());
        } else {
            throw new ServiceException(AlipayExceptionEnum.ALIPAY_CREATE_ERROR.getMsg());
        }
    }

    @RequestMapping("notifyUrlAlipay")
    public void notifyUrlAlipay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals("sign_type")) {
                continue;
            }
            String[] value = entry.getValue();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                if (i == value.length - 1) {
                    buffer.append(value[i]);
                } else {
                    buffer.append(value[i]).append(",");
                }
            }
            params.put(key, buffer.toString());
        }
        Message message = alipayService.notifyUrlAlipay(params);
        PrintWriter writer = response.getWriter();
        if ("200".equals(message.getCode())) {
            writer.print("success");
        } else {
            writer.print("failure");
        }
    }
}
