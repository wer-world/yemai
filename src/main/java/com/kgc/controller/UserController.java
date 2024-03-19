package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Message register(@RequestBody Map<String, Object> map){
        Object userObj  = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj),User.class);
        return userService.register(user);
    }


    @RequestMapping("checkLoginName")
    public Message checkLoginName(@RequestBody Map<String, Object> map){
        String loginName = (String)map.get("loginName") ;
        return userService.checkLoginName(loginName);
    }

    @RequestMapping("checkLogin")
    public Message checkLogin(@RequestBody Map<String, Object> map){
        String loginName = (String)map.get("loginName") ;
        return userService.checkLogin(loginName);
    }

    @GetMapping("delUser")
    public Message delUser(User user) {
        return Message.success();
    }
}
