package com.kgc.util;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Alipay;
import com.kgc.entity.Message;
import com.kgc.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 易买网定时器工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-15:46
 */
@Component
public class EasyBuyScheduleUtil {

    @Autowired
    private AlipayService alipayService;

    /**
     * 定时扫描支付宝支付表未完成订单
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void alipaySchedule() {
        List<Alipay> alipayList = alipayService.getAlipayList();
        for (Alipay alipay : alipayList) {
            Map<String, String> params = (Map<String, String>) JSON.parse(alipay.getParams());
            alipayService.replayAlipay(params);
        }
    }

}
