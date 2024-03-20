package com.kgc.service.impl;

import com.kgc.config.TokenConfig;
import com.kgc.dao.UserDao;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import com.kgc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理业务实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-18:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public Message login(User user) {
        // 1、用户登录业务
        if (user.getLoginName() == null || user.getLoginName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            return new Message("400", "fail", null);
        }
        User loginUser = userDao.loginCheck(user);
        // 2、判断登录状态
        if (loginUser != null) {
            // 登录成功添加令牌
            String token = JWTUtil.getToken(user, tokenConfig.getTokenSign(), tokenConfig.getTokenTimeOut());
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, tokenConfig.getTokenOverHours(), TimeUnit.HOURS);
            Cookie cookie = new Cookie("token", token);
            return Message.success(cookie);
        }
        return Message.error();
    }

    @Override
    public Message register(User user) {
        Message message = null;
        if (user == null) {
            message = new Message("400", "fail", null);
            return message;
        }
        int count = userDao.registerUser(user);
        if (count == 1) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message checkLoginName(String loginName) {
        Message message = null;
        if (loginName == null || loginName.isEmpty()) {
            message = new Message("400", "请填写登录名", null);
            return message;
        }
        User user = userDao.checkLoginName(loginName);
        if (user == null) {
            message = new Message("200", "该账号可以使用", user);
        } else {
            message = new Message("400", "该账号已存在不能注册", user);
        }
        return message;
    }

    @Override
    public Message checkLogin(String loginName) {
        Message message = null;
        if (loginName == null || loginName.isEmpty()) {
            message = new Message("400", "请填写登录名", null);
            return message;
        }
        User user = userDao.checkLoginName(loginName);
        if (user != null) {
            message = new Message("200", "该账号可以使用", user);
        } else {
            message = new Message("400", "该账号不存在", user);
        }
        return message;
    }

    @Override
    public Message findPsw(User user) {
        if (user == null) {
            return Message.error();
        }
        int count = userDao.findPsw(user);
        if (count == 1) {
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message identityCheck(String identityCode) {
        if (identityCode == null || "".equals(identityCode)) {
            return Message.error();
        }
        User user = userDao.identityCheck(identityCode);
        if (user == null) {
            return Message.success(user);
        }
        return Message.error();
    }

    @Override
    public Message getUser(User user) {
        User newUser = userDao.getUser(user);
        if (newUser != null) {
            return Message.success(newUser);
        }
        return Message.error();
    }
}
