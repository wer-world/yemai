package com.kgc.service.impl;

import com.kgc.config.TokenConfig;
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

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public Message login(User user) {
        // 1、用户登录业务
        if (user.getLoginName() == null || user.getLoginName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            return new Message("400", "fail", null);
        }
        User loginUser = userDao.checkLogin(user); // 重大业务错误需要重新编写
        if (loginUser == null) {
            return Message.error();
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            return Message.error("密码不正确");
        }
        // 2、判断登录状态
        if (loginUser != null) {
            // 登录成功添加令牌
            String token = JWTUtil.getToken(loginUser, tokenConfig.getTokenSign(), tokenConfig.getTokenTimeOut());
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
        User user = userDao.checkName(loginName);
        if (user != null) {
            message = new Message("200", "该账号可以使用", user);
        } else {
            message = new Message("400", "该账号不存在", user);
        }
        return message;
    }

    public Message checkRegisterName(String loginName) {
        Message message = null;
        if (loginName == null || loginName.isEmpty()) {
            message = new Message("400", "请填写登录名", null);
            return message;
        }
        User user = userDao.checkName(loginName);
        if (user == null) {
            message = new Message("200", "该账号可以使用", user);
        } else {
            message = new Message("400", "该账号已存在", user);
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

    @Override
    public Message getUserListPage(Map<String, Object> paramMap) {
        String userName = (String) paramMap.get("userName");
        String typeStr = (String) paramMap.get("type");
        Integer type = 0;
        if (typeStr != null && !"".equals(typeStr)) {
            type = Integer.valueOf(typeStr.toString());
        }

        User user = new User();
        user.setType(type);
        user.setUserName(userName);
        Integer currentPage = (Integer) paramMap.get("currentPage");
        Integer pageSize = (Integer) paramMap.get("pageSize");
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        Integer from = (currentPage - 1) * pageSize;
        long totalCount = userDao.getUserCount(type, userName);
        List<User> userList = userDao.getUserListPage(from, pageSize, type, userName);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("userList", userList);
        return Message.success(map);
    }

    @Override
    public Message checkType(User user) {
        User targetUser = userDao.getUser(user);
        User currentUser = ThreadLocalUtil.get();
        if (currentUser.getType() <= targetUser.getType()) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message updateUser(User user) {
        Integer affectRow = userDao.updateUser(user);
        if (affectRow < 1) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message deleteUser(User user) {
        Integer affectRow = userDao.updateUser(user);
        if (affectRow < 1) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message getCurrentUser() {
        User currentUser = ThreadLocalUtil.get();
        if (currentUser == null) {
            return Message.error();
        }
        return Message.success(currentUser);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public Message modifyPasswordById(User user) {
        if (user == null) {
            return Message.error();
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            return Message.error("请输入密码");
        }
        int count = userDao.modifyPasswordById(user);
        if (count == 0) {
            return Message.error();
        }
        return Message.success(count);
    }
}
