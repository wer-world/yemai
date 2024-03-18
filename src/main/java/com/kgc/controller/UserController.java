package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("register")
    public Message register(@RequestBody User user) {
        return null;
    }

    @GetMapping("delUser")
    public Message delUser(User user) {
        return Message.success();
    }
}
