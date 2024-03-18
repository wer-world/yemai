package com.kgc.service;

import com.kgc.dao.UserMapper;
import com.kgc.entity.Message;
import com.kgc.entity.User;

public interface UserService {

    public Message registUser(User user);
    public Message checkLoginName(String loginName);

    public Message checkLogin(String loginName);
    public Message loginCheck(String loginName,String password);
}
