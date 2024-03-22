package com.kgc.service;

import com.kgc.entity.Message;
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

    Message checkRegisterName(String loginName);

    /**
     * 检查用户登录名
     *
     * @param loginName 需要校验的用户名
     * @return 返回消息类
     */
    Message checkLoginName(String loginName);



    /**
     * 找回密码
     *
     * @param user 找回密码所对应的用户
     * @return
     */
    Message findPsw(User user);

    /**
     * 校验身份证
     *
     * @param identityCode 校验所需的身份证
     * @return
     */
    Message identityCheck(String identityCode);

    /**
     * 获取用户信息
     *
     * @param user 需要获取的条件(id)
     * @return 返回消息类
     */
    Message getUser(User user);

    /**
     * 查询所有用户
     * 前端选择传参（用户类型type，用户名userName，当前页码currentPage,页面容量pageSize）
     *
     * @param paramMap
     * @return
     */
    public Message getUserListPage(Map<String, Object> paramMap);

    /**
     * 查询用户信息（前端传参id）
     *
     * @param user
     * @return
     */
    Message checkType(User user);

    Message updateUser(User user);

    Message deleteUser(User user);

    Message getCurrentUser();
    /**
     * 通过用户id查询用户
     *
     * @param id 用户id
     * @return 返回用户
     */
    User getUserById(Integer id);

    /**
     * 通过用户id修改用户密码
     *
     * @param user
     * @return 返回受影响的行数
     */
    Message modifyPasswordById(User user);
}
