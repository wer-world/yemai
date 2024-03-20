package com.kgc;

import com.kgc.config.TokenConfig;
import com.kgc.entity.User;
import com.kgc.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 令牌测试类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-22:02
 */
@SpringBootTest
public class JWTTest {

    @Autowired
    private TokenConfig tokenConfig;

    @Test
    public void jwtTest() {
        User user = new User();
        user.setUserName("张三");
        user.setId(2);
        user.setType(0);
        String token = JWTUtil.getToken(user, tokenConfig.getTokenSign(), tokenConfig.getTokenTimeOut());
        System.out.println(token);

        User parseJWT = JWTUtil.parseToken(token, tokenConfig.getTokenSign());
        System.out.println(parseJWT);
    }
}
