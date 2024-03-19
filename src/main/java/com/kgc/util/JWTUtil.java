package com.kgc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kgc.constant.EasyBuyConstant;
import com.kgc.entity.User;
import com.kgc.enums.ExceptionEnum;
import com.kgc.exception.LoginException;

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
        if (user == null || user.getId() == null || user.getUserName() == null || user.getType() == null) {
            throw new LoginException(ExceptionEnum.BODY_NOT_MATCH.getMessage());
        }
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
        if (token == null || token.isEmpty()) {
            throw new LoginException(ExceptionEnum.NOT_TOKEN.getMessage());
        }
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(EasyBuyConstant.TOKEN_SIGN)).build().verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = new User();
            user.setId(claims.get("id").asInt());
            user.setUserName(claims.get("username").asString());
            user.setType(claims.get("type").asInt());
            return user;
        } catch (SignatureVerificationException e) {
            throw new LoginException(ExceptionEnum.SIGNATURE_NOT_MATCH.getMessage());
        } catch (TokenExpiredException e) {
            throw new LoginException(ExceptionEnum.TOKEN_OVER.getMessage());
        } catch (AlgorithmMismatchException e) {
            throw new LoginException(ExceptionEnum.VISA_ALGORITHM.getMessage());
        } catch (Exception e) {
            throw new LoginException(ExceptionEnum.LOGIN_ERROR.getMessage());
        }
    }
}
