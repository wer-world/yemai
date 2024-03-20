package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.entity.User;

import java.util.Map;

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

    /**
     * 检查用户登录名
     *
     * @param loginName 需要校验的用户名
     * @return 返回消息类
     */
    Message checkLoginName(String loginName);

    /**
     * 登录检查(可优化，一般登录不需要失焦校验，避免性能浪费)
     *
     * @param loginName 需要登录的用户名
     * @return 返回消息类
     */
    Message checkLogin(String loginName);

    /**
     * 找回密码
     * @param user 找回密码所对应的用户
     * @return
     */
    Message findPsw(User user);

    /**
     * 校验身份证
     * @param identityCode 校验所需的身份证
     * @return
     */
    Message identityCheck(String identityCode);


}
