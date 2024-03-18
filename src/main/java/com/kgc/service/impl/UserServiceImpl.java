package com.kgc.service.impl;

import com.kgc.dao.UserMapper;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Message registUser(User user) {
        Message message = null;
        if (user == null){
            message = new Message("400","fail",null);
            return message;
        }
        userMapper.registUser(user);
        message = new Message("200","success",null);
        return message;
    }

    @Override
    public Message checkLoginName(String loginName) {
        Message message = null;
        if (loginName == null || "".equals(loginName)){
            message = new Message("400","请填写登录名",null);
            return message;
        }
        User user = userMapper.checkLoginName(loginName);
        if (user == null){
            message = new Message("200","该账号可以使用",user);
        }else {
            message = new Message("400","该账号已存在不能注册",user);
        }
        return message;
    }

    @Override
    public Message checkLogin(String loginName) {
        Message message = null;
        if (loginName == null || "".equals(loginName)){
            message = new Message("400","请填写登录名",null);
            return message;
        }
        User user = userMapper.checkLoginName(loginName);
        if (user != null){
            message = new Message("200","该账号可以使用",user);
        }else {
            message = new Message("400","该账号不存在",user);
        }
        return message;
    }

    @Override
    public Message loginCheck(String loginName, String password) {
        Message message = null;
        if (loginName == null || "".equals(loginName) || password == null || "".equals(password)){
            message = new Message("400","fail",null);
            return message;
        }
        User user1 = new User();
        user1.setLoginName(loginName);
        user1.setPassword(password);
        User user = userMapper.loginCheck(user1);
        if (user != null){
            message = new Message("200","登录成功",user);
        }else {
            message = new Message("400","登录失败",user);
        }
        return message;
    }
}
