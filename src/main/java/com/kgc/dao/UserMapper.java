package com.kgc.dao;

import com.kgc.entity.User;

public interface UserMapper {
    public User registUser(User user);
    public User checkLoginName(String loginName);
    public User checkLogin(String loginName);
    public User loginCheck(User user);
}
