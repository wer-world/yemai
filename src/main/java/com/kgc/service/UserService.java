package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.User;

/**
 * 用户操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-18:14
 */
public interface UserService {
    /**
     * 登录
     *
     * @param user 用户账号
     * @return 返回消息类
     */
    Message login(User user);

    /**
     * 注册
     *
     * @param user 注册用户
     * @return 返回消息类
     */
    Message register(User user);

    Message registUser(User user);

    Message checkLoginName(String loginName);

    Message checkLogin(String loginName);

    Message loginCheck(String loginName, String password);
}
