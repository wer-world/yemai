package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.constant.EasyBuyConstant;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import com.kgc.service.UserService;
import com.kgc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private UserService userService;

    @PostMapping("login")
    public Message login(@RequestBody User user, HttpServletResponse response) {
        Message message = userService.login(user);
        // 3、成功
        if ("200".equals(message.getCode())) {
            response.addCookie((Cookie) message.getData());
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

    @GetMapping("delUser")
    public Message delUser(User user) {
        return Message.success();
    }
}
