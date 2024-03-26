package com.kgc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kgc.dao.OrderDao;
import com.kgc.entity.*;
import com.kgc.enums.OrderExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.*;
import com.kgc.util.ThreadLocalUtil;
import io.lettuce.core.pubsub.PubSubOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

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

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyCarService buyCarService;

    @Override
    public void fulfilOrderPay(String orderNumber) {
        // 完成订单状态更改
        Integer flag = orderDao.fulfilOrderPay(orderNumber);
        if (flag == 0) {
            throw new ServiceException("OrderServiceImpl fulfilOrderPay " + OrderExceptionEnum.ORDER_TABLE_STATUS.getMessage(), OrderExceptionEnum.ORDER_TABLE_STATUS.getMsg());
        }
    }

    @Override
    @Transactional
    public Message createOrder(List<OrderDetail> orderDetailList, User user) {
        // 1、创建订单类
        Order order = new Order();
        user = userService.getUser(user);
        if (user == null) {
            throw new ServiceException("OrderServiceImpl createOrder " + OrderExceptionEnum.USER_NOT_EXIST.getMessage(), OrderExceptionEnum.USER_NOT_EXIST.getMsg());
        }
        order.setUserId(user.getId());
        order.setLoginName(user.getLoginName());
        order.setUserAddress(user.getAddress());
        // 2.1 生成订单号
        String orderNum = UUID.randomUUID().toString();
        order.setSerialNumber(orderNum);
        Integer flag = orderDao.addOrder(order);
        if (flag == 0) {
            // 使事务生效
            throw new ServiceException("OrderServiceImpl createOrder " + OrderExceptionEnum.CREATE_ORDER_ERROR.getMessage(), OrderExceptionEnum.CREATE_ORDER_ERROR.getMsg());
        }
        // 3、添加商品，并计算商品价格，检查商品库存是否充足，加入订单详情信息类
        double orderSum = 0; // 计算订单总价格
        for (OrderDetail orderDetail : orderDetailList) {
            Product product = productService.getProductById(orderDetail.getProductId());
            if (product == null) {
                throw new ServiceException("OrderServiceImpl createOrder " + OrderExceptionEnum.PRODUCT_GET_ERROR.getMessage(), OrderExceptionEnum.PRODUCT_GET_ERROR.getMsg());
            }
            // 判断库存是否充足
            if (product.getStock() <= 0 || product.getStock() < orderDetail.getQuantity()) {
                throw new ServiceException("OrderServiceImpl createOrder " + OrderExceptionEnum.PRODUCT_STOCK_LACK.getMessage(), OrderExceptionEnum.PRODUCT_STOCK_LACK.getMsg());
            }
            orderDetail.setOrderId(order.getId());
            // 计算价格
            double orderDetailSum = orderDetail.getQuantity() * product.getPrice();
            orderSum += orderDetailSum;
            orderDetail.setCost(orderDetailSum); // 设置订单详情总价格
            // 更新库存
            product.setStock(product.getStock() - orderDetail.getQuantity());
            productService.modProduct(product);
            orderDetailService.addOrderDetail(orderDetail);
        }
        // 4、计算总价格加入订单属性
        order.setCost(orderSum);
        flag = orderDao.modOrder(order);
        if (flag == 0) {
            throw new ServiceException("OrderServiceImpl createOrder " + OrderExceptionEnum.ORDER_COST_UPDATE_ERROR.getMessage(), OrderExceptionEnum.ORDER_COST_UPDATE_ERROR.getMsg());
        }

        // 5、清空购物车
        buyCarService.delBuyCarProductByUserId();
        return Message.success(order);
    }

    @Override
    @Transactional
    public Message cancelOrder(Order order) {
        // 获取当前订单的所有下单商品
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailListByOrderId(order.getId());
        if (orderDetailList == null) {
            throw new ServiceException("OrderServiceImpl cancelOrder " + OrderExceptionEnum.ORDER_DETAIL_LIST_GET_ERROR.getMessage(), OrderExceptionEnum.ORDER_DETAIL_LIST_GET_ERROR.getMsg());
        }
        logger.debug("OrderServiceImpl cancelOrder find all orderDetailList:" + orderDetailList);
        for (OrderDetail orderDetail : orderDetailList) {
            Product product = productService.getProductById(orderDetail.getProductId());
            if (product == null) {
                throw new ServiceException("OrderServiceImpl cancelOrder " + OrderExceptionEnum.ORDER_DETAIL_GET_ERROR.getMessage(), OrderExceptionEnum.ORDER_DETAIL_GET_ERROR.getMsg());
            }
            product.setStock(product.getStock() + orderDetail.getQuantity());
            logger.debug("OrderServiceImpl cancelOrder update product:" + product);
            // 更新对应商品库存
            productService.modProduct(product);
        }
        // 逻辑删除订单详情表
        orderDetailService.delOrderDetailByOrderId(order.getId());

        // 更新订单状态
        Integer flag = orderDao.cancelOrder(order);
        if (flag == 0) {
            throw new ServiceException("OrderServiceImpl cancelOrder " + OrderExceptionEnum.ORDER_TABLE_STATUS.getMessage(), OrderExceptionEnum.ORDER_TABLE_STATUS.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message getOrderList(Pages pages, Order order) {
        Page<Object> page = PageHelper.startPage(pages.getCurrentPage(), pages.getPageSize());
        List<Order> orderList = orderDao.getOrderList(order);
        if (orderList != null && !orderList.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderList", orderList);
            resultMap.put("totalCount", page.getTotal());
            return Message.success(resultMap);
        }
        return Message.error();
    }

    @Override
    public Order getOrder(Order order) {
        return orderDao.getOrder(order);
    }

    @Override
    public List<Order> getTimeOutOrderList() {
        return orderDao.getTimeOutOrderList();
    }
}
