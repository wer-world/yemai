package com.kgc.controller;

import com.kgc.entity.BuyCar;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.BuyCarService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 购物车管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-22-10:49
 */
@RestController
@RequestMapping("buyCar")
public class BuyCarController {
    @Autowired
    private BuyCarService buyCarService;

    @PostMapping("getBuyCarListPage")
    public Message getBuyCarListPage(@RequestBody Map<String, Object> params) {
        return buyCarService.getBuyCarList(params);
    }

    @PostMapping("delBuyCarProduct")
    public Message delBuyCarProduct(@RequestBody BuyCar buyCar) {
        return buyCarService.delBuyCarProduct(buyCar);
    }

    @GetMapping("getBuyCarListByUserId")
    public Message getBuyCarListByUserId() {
        User user = ThreadLocalUtil.get();
        return buyCarService.getBuyCarListByUserId(user.getId());
    }

    @PostMapping("addBuyCar")
    public Message addBuyCar(@RequestBody BuyCar buyCar) {
        User user = ThreadLocalUtil.get();
        buyCar.setUserId(user.getId());
        if (buyCar.getProductNum() == null) {
            buyCar.setProductNum(1);
        }
        return buyCarService.addBuyCar(buyCar);
    }

    @DeleteMapping("delBuyCarProductById")
    public Message delBuyCarProductById(Integer id) {
        return buyCarService.delBuyCarProductById(id);
    }
}
