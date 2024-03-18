package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.constant.EasyBuyConstant;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import com.kgc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private UserService userService;
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

    @RequestMapping("register")
    public Message regist(@RequestBody Map map){
        Object userObj  = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj),User.class);
        Message msg = userService.registUser(user);
        return msg;
    }


    @RequestMapping("checkLoginName")
    public Message checkLoginName(@RequestBody Map map){
        String loginName = (String)map.get("loginName") ;
        Message msg = userService.checkLoginName(loginName);
        return msg;
    }

    @RequestMapping("checkLogin")
    public Message checkLogin(@RequestBody Map map){
        String loginName = (String)map.get("loginName") ;
        Message msg = userService.checkLogin(loginName);
        return msg;
    }
    @RequestMapping("loginCheck")
    public Message loginCheck(@RequestBody Map map,HttpServletResponse response){
        String loginName = (String)map.get("loginName") ;
        String password = (String)map.get("password") ;
        Message msg = userService.loginCheck(loginName,password);
        return msg;
    }
}
