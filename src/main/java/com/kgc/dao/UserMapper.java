package com.kgc.dao;

import com.kgc.entity.User;

public interface UserMapper {
    /**
     * 注册用户，添加到用户表
     *
     * @param user 需要注册的用户
     * @return 返回用户(需优化为返回数据插入的主键)
     */
    public int registerUser(User user);

    /**
     * 检查登录名
     *
     * @param loginName 需要检查的登录名
     * @return 返回用户
     */
    public User checkLoginName(String loginName);

    public User checkLogin(String loginName);

    /**
     * 登录检查
     *
     * @param user 需要检查的用户
     * @return 返回用户名
     */
    public User loginCheck(User user);

    /**
     * 忘记密码修改密码
     *
     * @param user 需要修改的用户
     * @return 返回受影响的行数
     */
    public int findPsw(User user);
    /**
     * 校验身份证是否重复
     *
     * @param identityCode 需要修改的用户
     * @return 返回受影响的行数
     */
    public User identityCheck(String identityCode);
}
