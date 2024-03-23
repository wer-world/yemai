package com.kgc.service.impl;

import com.kgc.dao.BuyCarDao;
import com.kgc.entity.BuyCar;
import com.kgc.entity.Message;
import com.kgc.service.BuyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Message getBuyCarList(Map<String, Object> params) {
        return null;
    }

    @Override
    public Message delBuyCarProduct(BuyCar buyCar) {
        return null;
    }

    @Override
    public Message delBuyCarProductById(Integer id) {
        Integer flag = buyCarDao.delBuyCarProductById(id);
        if (flag > 0) {
            return Message.success();
        }
        return Message.error();
    }
}
