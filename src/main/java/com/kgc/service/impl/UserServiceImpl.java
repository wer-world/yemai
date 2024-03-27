package com.kgc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kgc.config.TokenConfig;
import com.kgc.dao.UserDao;
import com.kgc.entity.Message;
import com.kgc.entity.Pages;
import com.kgc.entity.User;
import com.kgc.enums.LoginExceptionEnum;
import com.kgc.enums.UserExceptionEnum;
import com.kgc.exception.LoginException;
import com.kgc.exception.ServiceException;
import com.kgc.service.UserService;
import com.kgc.util.JWTUtil;
import com.kgc.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
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
        // 判断登录状态
        User loginUser = userDao.checkLogin(user);
        if (loginUser == null || !loginUser.getPassword().equals(user.getPassword())) {
            return Message.loginError("用户名或密码错误");
        }
        // 登录成功添加令牌
        String token = JWTUtil.getToken(loginUser, tokenConfig.getTokenSign(), tokenConfig.getTokenTimeOut());
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token, token, tokenConfig.getTokenOverHours(), TimeUnit.HOURS);
        List<Cookie> cookieList = getStringCookieList(token, loginUser);
        return Message.success(cookieList);
    }

    private List<Cookie> getStringCookieList(String token, User loginUser) {
        List<Cookie> cookieList = new ArrayList<>();
        Cookie tokenCookie = new Cookie("token", token);
        Cookie loginNameCookie = new Cookie("loginName", loginUser.getLoginName());
        Cookie typeCookie = new Cookie("type", loginUser.getType().toString());
        tokenCookie.setMaxAge(tokenConfig.getTokenOverHours() * 60 * 60);
        loginNameCookie.setMaxAge(tokenConfig.getTokenOverHours() * 60 * 60);
        typeCookie.setMaxAge(tokenConfig.getTokenOverHours() * 60 * 60);
        tokenCookie.setPath("/");
        loginNameCookie.setPath("/");
        typeCookie.setPath("/");
        cookieList.add(tokenCookie);
        cookieList.add(loginNameCookie);
        cookieList.add(typeCookie);
        return cookieList;
    }

    @Override
    @Transactional
    public Message register(User user) {
        Integer flag = userDao.registerUser(user);
        if (flag == 0) {
            throw new LoginException("UserServiceImpl register " + LoginExceptionEnum.REGISTER_STATUS_ERROR.getMessage(), LoginExceptionEnum.REGISTER_STATUS_ERROR.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message checkLoginName(String loginName) {
        User user = userDao.checkName(loginName);
        if (user != null) {
            return Message.success(user);
        }
        return Message.loginError("该账号不存在");
    }

    public Message checkRegisterName(String loginName) {
        User user = userDao.checkName(loginName);
        if (user != null) {
            return Message.loginError("该账号不可使用");
        }
        return Message.success("该账号可以使用");
    }


    @Override
    public Message findPsw(User user) {
        int count = userDao.findPsw(user);
        if (count == 0) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message identityCheck(String identityCode) {
        User user = userDao.identityCheck(identityCode);
        if (user != null) {
            return Message.error();
        }
        return Message.success(user);
    }

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    public Message getUserListPage(Pages pages, User user) {
        Page<Object> page = PageHelper.startPage(pages.getCurrentPage(), pages.getPageSize());
        List<User> userList = userDao.getUserListPage(user);
        if (userList != null && !userList.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("totalCount", page.getTotal());
            map.put("userList", userList);
            return Message.success(map);
        }
        return Message.error();
    }

    @Override
    public Message checkType(User user) {
        User targetUser = userDao.getUserById(user.getId());
        User currentUser = ThreadLocalUtil.get();
        if (currentUser.getType() >= targetUser.getType()) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    @Transactional
    public Message updateUser(User user) {
        Integer affectRow = userDao.updateUser(user);
        if (affectRow == 0) {
            throw new ServiceException("UserServiceImpl updateUser " + UserExceptionEnum.USER_UPDATE_FAILURE.getMessage(), UserExceptionEnum.USER_UPDATE_FAILURE.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message deleteUser(User user) {
        Integer affectRow = userDao.deleteUser(user);
        if (affectRow == 0) {
            throw new ServiceException("UserServiceImpl deleteUser " + UserExceptionEnum.USER_DELETE_FAILURE.getMessage(), UserExceptionEnum.USER_DELETE_FAILURE.getMsg());
        }
        return Message.success();
    }

    @Override
    public Message getUserById(Integer id) {
        User user = userDao.getUserById(id);
        if (user != null) {
            return Message.success(user);
        }
        return Message.error();
    }

    @Override
    public Message modifyPasswordById(User user) {
        Integer flag = userDao.modifyPasswordById(user);
        if (flag == 0) {
            throw new ServiceException("UserServiceImpl modifyPasswordById " + UserExceptionEnum.UPDATE_PASSWORD_FAILURE.getMessage(), UserExceptionEnum.UPDATE_PASSWORD_FAILURE.getMsg());
        }
        return Message.success(flag);
    }

    @Override
    public Message loginOut(User user) {
        Boolean isLoginOut = stringRedisTemplate.delete(user.getToken());
        if (Boolean.TRUE.equals(isLoginOut)) {
            List<Cookie> cookieList = getStringCookieList();
            return Message.success(cookieList);
        }
        return Message.error();
    }

    private List<Cookie> getStringCookieList() {
        List<Cookie> cookieList = new ArrayList<>();
        Cookie tokenCookie = new Cookie("token", null);
        Cookie loginNameCookie = new Cookie("loginName", null);
        Cookie typeCookie = new Cookie("type", null);
        tokenCookie.setMaxAge(0);
        loginNameCookie.setMaxAge(0);
        typeCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        loginNameCookie.setPath("/");
        typeCookie.setPath("/");
        cookieList.add(tokenCookie);
        cookieList.add(loginNameCookie);
        cookieList.add(typeCookie);
        return cookieList;
    }
}
