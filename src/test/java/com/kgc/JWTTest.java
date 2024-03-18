package com.kgc;

import com.kgc.entity.User;
import com.kgc.util.JWTUtil;
import org.junit.jupiter.api.Test;

/**
 * 令牌测试类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-22:02
 */
public class JWTTest {

    @Test
    public void jwtTest(){
        User user = new User();
        user.setUserName("张三");
        user.setId(2);
        user.setType(0);
        String token = JWTUtil.getToken(user);
        System.out.println(token);

        User parseJWT = JWTUtil.parseToken(token);
        System.out.println(parseJWT);
    }
}
