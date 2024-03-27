package com.kgc.service.impl;

import com.kgc.dao.BuyCarDao;
import com.kgc.entity.BuyCar;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.enums.BuyCarExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.BuyCarService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 购物车业务实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-10:59
 */
@Service
public class BuyCarServiceImpl implements BuyCarService {

    @Autowired
    private BuyCarDao buyCarDao;

    @Override
    public Message getBuyCarListByUserId(Integer userId) {
        List<BuyCar> buyCarList = buyCarDao.getBuyCarListByUserId(userId);
        if (buyCarList != null && !buyCarList.isEmpty()) {
            return Message.success(buyCarList);
        }
        return Message.error();
    }

    @Override
    public Message addBuyCar(BuyCar buyCar) {
        List<BuyCar> buyCarList = buyCarDao.getBuyCar(buyCar);
        if (buyCarList != null && !buyCarList.isEmpty()) {
            return Message.error("购物车中该数据已添加,请勿重复添加!");
        }
        Integer flag = buyCarDao.addBuyCar(buyCar);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message modBuyCarProductNumById(BuyCar buyCar) {
        Integer flag = buyCarDao.modBuyCar(buyCar);
        if (flag == 0){
            throw new ServiceException(BuyCarExceptionEnum.DELETE_UPDATE_FAILURE.getMessage(), BuyCarExceptionEnum.DELETE_UPDATE_FAILURE.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message delBuyCarProductById(Integer id) {
        BuyCar buyCar = new BuyCar();
        buyCar.setId(id);
        Integer flag = buyCarDao.delBuyCarProduct(buyCar);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    @Transactional
    public Message delBuyCarProductByUserId() {
        User user = ThreadLocalUtil.get();
        BuyCar buyCar = new BuyCar();
        buyCar.setUserId(user.getId());
        Integer flag = buyCarDao.delBuyCarProduct(buyCar);
        if (flag == 0) {
            throw new ServiceException(BuyCarExceptionEnum.DELETE_PRODUCT_FAILURE.getMessage(), BuyCarExceptionEnum.DELETE_PRODUCT_FAILURE.getMsg());
        }
        return Message.success();
    }
}
