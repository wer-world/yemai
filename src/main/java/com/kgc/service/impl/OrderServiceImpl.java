package com.kgc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kgc.dao.OrderDao;
import com.kgc.entity.*;
import com.kgc.enums.OrderExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.*;
import com.kgc.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
            logger.error("OrderServiceImpl fulfilOrderPay fulfil update order status error");
            throw new ServiceException(OrderExceptionEnum.ORDER_TABLE_STATUS.getMsg());
        }
    }

    @Override
    public Message createOrder(List<OrderDetail> orderDetailList) {
        // 1、创建订单类
        Order order = new Order();
        // 2、添加用户ThreadLocalUtil获取用户
        User user = ThreadLocalUtil.get();
        if (user == null) {
            logger.error("OrderServiceImpl createOrder user not exist");
            throw new ServiceException(OrderExceptionEnum.USER_NOT_EXIST.getMsg());
        }
        user = (User) userService.getUser(user).getData();
        order.setUserId(user.getId());
        order.setLoginName(user.getLoginName());
        order.setUserAddress(user.getAddress());
        // 2.1 生成订单号
        String orderNum = UUID.randomUUID().toString();
        order.setSerialNumber(orderNum);
        Integer flag = orderDao.addOrder(order);
        if (flag == 0) {
            // 使事务生效
            logger.error("OrderServiceImpl createOrder order add error");
            throw new ServiceException(OrderExceptionEnum.CREATE_ORDER_ERROR.getMsg());
        }
        // 3、添加商品，并计算商品价格，检查商品库存是否充足，加入订单详情信息类
        double orderSum = 0; // 计算订单总价格
        for (OrderDetail orderDetail : orderDetailList) {
            Message message = productService.getProductById(orderDetail.getProductId());
            if (!"200".equals(message.getCode())) {
                logger.error("OrderServiceImpl createOrder product get error");
                throw new ServiceException(OrderExceptionEnum.PRODUCT_GET_ERROR.getMsg());
            }
            Product product = (Product) message.getData();
            // 判断库存是否充足
            if (product.getStock() <= 0 || product.getStock() < orderDetail.getQuantity()) {
                logger.error("OrderServiceImpl createOrder product inventory shortage");
                throw new ServiceException(OrderExceptionEnum.PRODUCT_STOCK_LACK.getMsg());
            }
            orderDetail.setOrderId(order.getId());
            // 计算价格
            double orderDetailSum = orderDetail.getQuantity() * product.getPrice();
            orderSum += orderDetailSum;
            orderDetail.setCost(orderDetailSum); // 设置订单详情总价格
            // 更新库存
            product.setStock(product.getStock() - orderDetail.getQuantity());
            message = productService.modProduct(product);
            if (!"200".equals(message.getCode())) {
                logger.error("OrderServiceImpl createOrder product stock update error");
                throw new ServiceException(OrderExceptionEnum.PRODUCT_UPDATE_ERROR.getMsg());
            }
            Boolean isAdd = orderDetailService.addOrderDetail(orderDetail);
            if (!isAdd) {
                logger.error("OrderServiceImpl createOrder order detail create error");
                throw new ServiceException(OrderExceptionEnum.ORDER_DETAIL_CREATE_ERROR.getMsg());
            }
        }
        // 4、计算总价格加入订单属性
        order.setCost(orderSum);
        flag = orderDao.modOrder(order);

        // 5、清空购物车
        buyCarService.delBuyCarProductByUserId();

        if (flag == 0) {
            logger.error("OrderServiceImpl createOrder order cost update error");
            throw new ServiceException(OrderExceptionEnum.ORDER_COST_UPDATE_ERROR.getMsg());
        }
        return Message.success(order);
    }

    @Override
    public Message addOrder(Order order) {
        Integer flag = orderDao.addOrder(order);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message modOrder(Order order) {
        Integer flag = orderDao.modOrder(order);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message cancelOrder(Order order) {
        // 获取当前订单的所有下单商品
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailListByOrderId(order.getId());
        if (orderDetailList == null) {
            logger.error("OrderServiceImpl cancelOrder orderDetailList get error");
            throw new ServiceException();
        }
        logger.debug("OrderServiceImpl cancelOrder find all orderDetailList:" + orderDetailList);
        for (OrderDetail orderDetail : orderDetailList) {
            Message message = productService.getProductById(orderDetail.getProductId());
            if (!"200".equals(message.getCode())) {
                logger.error("OrderServiceImpl cancelOrder orderDetail get error");
                throw new ServiceException(OrderExceptionEnum.ORDER_DETAIL_GET_ERROR.getMsg());
            }
            Product product = (Product) message.getData();
            product.setStock(product.getStock() + orderDetail.getQuantity());
            logger.debug("OrderServiceImpl cancelOrder update product:" + product);
            // 更新对应商品库存
            message = productService.modProduct(product);
            if (!"200".equals(message.getCode())) {
                logger.error("OrderServiceImpl cancelOrder update product stock error");
                throw new ServiceException(OrderExceptionEnum.PRODUCT_UPDATE_ERROR.getMsg());
            }
        }
        // 逻辑删除订单详情表
        Message message = orderDetailService.delOrderDetailByOrderId(order.getId());
        if (!"200".equals(message.getCode())) {
            logger.error("OrderServiceImpl cancelOrder delete order detail info error");
            throw new ServiceException(OrderExceptionEnum.ORDER_DETAIL_DELETE_ERROR.getMsg());
        }
        // 更新订单状态
        Integer flag = orderDao.cancelOrder(order);
        if (flag == 0) {
            logger.error("OrderServiceImpl cancelOrder cancel order error");
            throw new ServiceException(OrderExceptionEnum.ORDER_TABLE_STATUS.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message getOrderList(Map<String, Object> params) {
        Integer currentPage = (Integer) params.get("currentPage");
        Integer pageSize = (Integer) params.get("pageSize");
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 5;
        }
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Order> orderList = orderDao.getOrderList(params);
        if (orderList != null && !orderList.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderList", orderList);
            resultMap.put("totalCount", page.getTotal());
            return Message.success(resultMap);
        }
        return Message.error();
    }

    @Override
    public Message getOrder(Order order) {
        Order resultOrder = orderDao.getOrder(order);
        if (resultOrder == null) {
            return Message.error();
        }
        return Message.success(resultOrder);
    }

    @Override
    public List<Order> getTimeOutOrderList() {
        return orderDao.getTimeOutOrderList();
    }
}
