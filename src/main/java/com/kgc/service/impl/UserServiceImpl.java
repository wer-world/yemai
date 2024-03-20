package com.kgc.service.impl;

import com.kgc.constant.EasyBuyConstant;
import com.kgc.dao.UserDao;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import com.kgc.util.JWTUtil;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            String token = JWTUtil.getToken(user);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, EasyBuyConstant.TOKEN_OVER_HOURS, TimeUnit.HOURS);
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
        if (count == 1){
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
        if (user == null){
            return  Message.error();
        }
        int count = userDao.findPsw(user);
        if (count == 1){
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message identityCheck(String identityCode) {
        if (identityCode == null || "".equals(identityCode)){
            return Message.error();
        }
        User user = userDao.identityCheck(identityCode);
        if (user == null){
            return Message.success(user);
        }
        return Message.error();
    }

    @Override
    public Message getUserListPage(Map<String,Object> paramMap) {
        String userName = (String) paramMap.get("userName");
        Integer type = (Integer) paramMap.get("type");
        User user = new User();
        user.setType(type);
        user.setUserName(userName);
        Integer currentPage = (Integer) paramMap.get("currentPage");
        Integer pageSize = (Integer) paramMap.get("pageSize");
        if (currentPage==null){
            currentPage = 1;
        }
        if (pageSize==null){
            pageSize=5;
        }
        Integer from = currentPage-1;
        long totalCount = userDao.getUserCount(type,userName);
        List<User> userList = userDao.getUserListPage(from,pageSize,type,userName);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("totalCount",totalCount);
        map.put("userList",userList);
        return Message.success(map);
    }

    @Override
    public Message getUser(User user) {
        User user1 = userDao.getUser(user);
        return Message.success(user1);
    }

    @Override
    public Message checkType(User user) {
        User targetUser = userDao.getUser(user);
        User currentUser = ThreadLocalUtil.get();
        if (currentUser.getType()<=targetUser.getType()){
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message updateUser(User user) {
        Integer affectRow = userDao.updateUser(user);
        if (affectRow<1){
            return  Message.error();
        }
        return Message.success();
    }

    @Override
    public Message deleteUser(User user) {
        Integer affectRow = userDao.updateUser(user);
        if (affectRow<1){
            return  Message.error();
        }
        return Message.success();
    }

    @Override
    public Message getCurrentUser() {
        User currentUser = ThreadLocalUtil.get();
        if (currentUser==null){
            return Message.error();
        }
        return Message.success(currentUser);
    }
}
