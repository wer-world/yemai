package com.kgc.service.impl;

import com.kgc.dao.OrderDao;
import com.kgc.exception.AlipayException;
import com.kgc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单业务实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-22:00
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderDao orderDao;

    @Override
    public void fulfilOrderPay(String orderNumber) {
        Integer flag = orderDao.fulfilOrderPay(orderNumber);
        if (flag == 0){
            logger.error("OrderServiceImpl fulfilOrderPay fulfil update order and order correlation table error");
            throw new AlipayException();
        }
    }
}
