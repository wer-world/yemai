package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Message;
import com.kgc.entity.Pages;
import com.kgc.entity.User;
import com.kgc.enums.EmailExceptionEnum;
import com.kgc.enums.LoginExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.UserService;
import com.kgc.util.EmailCodeUtil;
import com.kgc.util.PagesUtil;
import com.kgc.util.RedisUtil;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-21:06
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Message login(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        // 1、用户登录业务
        if (user.getLoginName() == null || user.getLoginName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            return Message.loginError("登录信息未填写!");
        }
        Message message = userService.login(user);
        // 3、成功
        if ("200".equals(message.getCode())) {
            List<Cookie> cookieList = (List<Cookie>) message.getData();
            for (Cookie cookie : cookieList) {
                response.addCookie(cookie);
            }
            return Message.success();
        }
        return Message.error();
    }

    @GetMapping("loginOut")
    public Message loginOut(HttpServletResponse response) {
        User user = ThreadLocalUtil.get();
        Message message = userService.loginOut(user);
        // 3、成功
        if ("200".equals(message.getCode())) {
            List<Cookie> cookieList = (List<Cookie>) message.getData();
            for (Cookie cookie : cookieList) {
                response.addCookie(cookie);
            }
            return Message.success();
        }
        return Message.error();
    }

    @RequestMapping("register")
    public Message register(@RequestBody Map<String, Object> map) {
        Object userObj = map.get("user");
        if (userObj == null) {
            return Message.loginError("注册信息未填写");
        }
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        return userService.register(user);
    }


    @RequestMapping("checkLoginName")
    public Message checkLoginName(@RequestBody Map<String, Object> map) {
        String loginName = (String) map.get("loginName");
        if (loginName == null || loginName.isEmpty()) {
            return Message.loginError("请填写登录名");
        }
        return userService.checkLoginName(loginName);
    }

    @RequestMapping("checkRegisterName")
    public Message checkRegisterName(@RequestBody Map<String, Object> map) {
        String loginName = (String) map.get("loginName");
        if (loginName == null || loginName.isEmpty()) {
            return Message.loginError("请填写登录名");
        }
        return userService.checkRegisterName(loginName);
    }


    @GetMapping("delUser")
    public Message delUser(User user) {
        return Message.success();
    }

    /**
     * 发送邮箱
     *
     * @param email
     */
    @RequestMapping("/sendEmailCode")
    public Message sendEmailCode(String email) {
        Session session = EmailCodeUtil.createSession();
        //	创建邮件对象
        MimeMessage message = new MimeMessage(session);
        String sixNum = EmailCodeUtil.getSixNum();
        try {
            message.setSubject("主题");
            message.setText(sixNum);
            message.setFrom(new InternetAddress("1348466224@qq.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            Transport.send(message);
        } catch (Exception e) {
            throw new ServiceException("UserController sendEmailCode " + EmailExceptionEnum.MAIL_OBJECT_CREATE_FAILURE.getMessage(), EmailExceptionEnum.MAIL_OBJECT_CREATE_FAILURE.getMsg());
        }
        redisUtil.setValueToRedis(sixNum, sixNum);
        return Message.success();
    }

    @RequestMapping("checkEmailCode")
    public Message checkEmailCode(String code) {
        if (code == null || "".equals(code)) {
            return Message.error("请填写验证码");
        }
        String valueForRedis = redisUtil.getValueForRedis(code);
        if (valueForRedis == null || "".equals(valueForRedis)) {
            return Message.error("验证失败");
        }
        return Message.success("验证成功");
    }

    @RequestMapping("findPsw")
    public Message findPsw(@RequestBody Map<String, Object> map) {
        Object userObj = map.get("user");
        if (userObj == null) {
            return Message.error("找回密码账号未填写");
        }
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        return userService.findPsw(user);
    }

    @RequestMapping("identityCheck")
    public Message identityCheck(@RequestBody Map<String, Object> map) {
        String identityCode = map.get("identityCode").toString();
        if (identityCode == null || identityCode.isEmpty()) {
            return Message.error();
        }
        return userService.identityCheck(identityCode);
    }

    @PostMapping("getUserListPage")
    public Message getUserListPage(@RequestBody Map<String, Object> paramMap) {
        String userName = (String) paramMap.get("userName");
        String typeStr = (String) paramMap.get("type");
        if (typeStr==null || typeStr.isEmpty()){
            typeStr = "0";
        }
        Integer type = Integer.parseInt(typeStr);
        Pages pages = PagesUtil.parseMapToPages(paramMap);
        User user = new User();
        user.setUserName(userName);
        user.setType(type);
        return userService.getUserListPage(pages, user);
    }

    @RequestMapping("getUser")
    public Message getUser(@RequestBody User user) {
        if (user.getId() == null) {
            return Message.error();
        }
        User resultUser = userService.getUser(user);
        if (resultUser != null) {
            return Message.success(resultUser);
        }
        return Message.error();
    }

    @RequestMapping("checkType")
    public Message checkType(@RequestBody User user) {
        if (user.getId() == null) {
            return Message.error();
        }
        return userService.checkType(user);
    }

    @RequestMapping("updateUser")
    public Message updateUser(@RequestBody Map<String, Object> map) {
        Object userObj = map.get("user");
        if (userObj == null) {
            return Message.error();
        }
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        return userService.updateUser(user);
    }

    @RequestMapping("deleteUser")
    public Message deleteUser(@RequestBody User user) {
        if (user.getId() == null) {
            return Message.error();
        }
        return userService.deleteUser(user);
    }

    @RequestMapping("modifyPasswordById")
    public Message modifyPasswordById(@RequestBody Map<String, Object> map) {
        Object userObj = map.get("user");
        if (userObj == null) {
            return Message.error();
        }
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Message.error("请输入密码");
        }
        return userService.modifyPasswordById(user);
    }

    @GetMapping("getUserById")
    public Message getUserById(User user) {
        return userService.getUserById(user.getId());
    }

    @GetMapping("getCurrentUser")
    public Message getCurrentUser() {
        User user = ThreadLocalUtil.get();
        if (user==null){
            return Message.error();
        }
        Message message = userService.getUserById(user.getId());
        return message;
    }
}
