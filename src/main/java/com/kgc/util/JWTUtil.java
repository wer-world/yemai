package com.kgc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kgc.constant.EasyBuyConstant;
import com.kgc.entity.User;

import java.util.Date;
import java.util.Map;

/**
 * token令牌生成类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-21:43
 */
public class JWTUtil {

    /**
     * 生成token
     *
     * @param user 登录用户
     * @return 返回token
     */
    public static String getToken(User user) {
        return JWT.create()
                .withClaim("id", user.getId()) // 设置载荷
                .withClaim("username", user.getUserName())
                .withClaim("type", user.getType())
                .withExpiresAt(new Date(System.currentTimeMillis() + EasyBuyConstant.TOKEN_OVER_TIME)) // 设置令牌过期时间
                .sign(Algorithm.HMAC256(EasyBuyConstant.TOKEN_SIGN));
    }

    /**
     * token令牌解析
     *
     * @param token 需要解析的令牌
     * @return 返回用户登录对象
     */
    public static User parseToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(EasyBuyConstant.TOKEN_SIGN)).build().verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        User user = new User();
        user.setId(claims.get("id").asInt());
        user.setUserName(claims.get("username").asString());
        user.setType(claims.get("type").asInt());
        return user;
    }
}
