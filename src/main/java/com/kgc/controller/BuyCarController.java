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

    @GetMapping("getBuyCarListByUserId")
    public Message getBuyCarListByUserId() {
        User user = ThreadLocalUtil.get();
        return buyCarService.getBuyCarListByUserId(user.getId());
    }

    @PostMapping("addBuyCar")
    public Message addBuyCar(@RequestBody BuyCar buyCar) {
        if (buyCar.getProductId() == null) {
            return Message.error("添加至购物车商品,商品id不能为空!");
        }
        User user = ThreadLocalUtil.get();
        buyCar.setUserId(user.getId());
        if (buyCar.getProductNum() == null) {
            buyCar.setProductNum(1);
        }
        return buyCarService.addBuyCar(buyCar);
    }

    @DeleteMapping("delBuyCarProductById")
    public Message delBuyCarProductById(Integer id) {
        if (id == null) {
            return Message.error("删除购物车商品,商品id不能为空!");
        }
        return buyCarService.delBuyCarProductById(id);
    }
}
