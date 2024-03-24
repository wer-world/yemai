package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.UserService;
import com.kgc.util.EmailCodeUtil;
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
    public Message login(@RequestBody Map map, HttpServletResponse response) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
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

    @RequestMapping("register")
    public Message register(@RequestBody Map<String, Object> map) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        return userService.register(user);
    }


    @RequestMapping("checkLoginName")
    public Message checkLoginName(@RequestBody Map<String, Object> map) {
        String loginName = (String) map.get("loginName");
        return userService.checkLoginName(loginName);
    }

    @RequestMapping("checkRegisterName")
    public Message checkRegisterName(@RequestBody Map<String, Object> map) {
        String loginName = (String) map.get("loginName");
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
            e.printStackTrace();
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
    public Message findPsw(@RequestBody Map map) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        if (user == null || "".equals(user)) {
            return Message.error();
        }
        Message message = userService.findPsw(user);
        return message;
    }

    @RequestMapping("identityCheck")
    public Message identityCheck(@RequestBody Map map) {
        String identityCode = (String) map.get("identityCode");
        if (identityCode == null || "".equals(identityCode)) {
            return Message.error();
        }
        Message message = userService.identityCheck(identityCode);
        return message;
    }

    @PostMapping("getUserListPage")
    public Message getUserListPage(@RequestBody Map<String, Object> paramMap) {
        return userService.getUserListPage(paramMap);
    }

    @RequestMapping("getUser")
    public Message getUser(@RequestBody User user) {
        return userService.getUser(user);
    }

    @RequestMapping("checkType")
    public Message checkType(@RequestBody User user) {
        return userService.checkType(user);
    }

    @RequestMapping("updateUser")
    public Message updateUser(@RequestBody Map map) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        if (user == null || "".equals(user)) {
            return Message.error();
        }
        return userService.updateUser(user);
    }

    @RequestMapping("deleteUser")
    public Message deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }


    @RequestMapping("getCurrentUser")
    public Message getCurrentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping("modifyPasswordById")
    public Message modifyPasswordById(@RequestBody Map map) {
        Object userObj = map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        return userService.modifyPasswordById(user);
    }

    @GetMapping("getUserById")
    public Message getUserById() {
        User user = ThreadLocalUtil.get();
        return userService.getUserById(user.getId());
    }
}
