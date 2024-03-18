package com.kgc.controller;

import com.kgc.constant.EasyBuyConstant;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-21:06
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("login")
    public Message login(User user, HttpServletResponse response) {
        // 1、用户登录业务
        // 2、判断登录状态
        // 3、成功
        if (true) {
            String token = JWTUtil.getToken(user);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, EasyBuyConstant.TOKEN_OVER_HOURS);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return Message.success();
        }

        return Message.error();
    }
}
