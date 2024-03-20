package com.kgc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kgc.entity.User;
import com.kgc.enums.TokenExceptionEnum;
import com.kgc.exception.ServiceException;

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
    public static String getToken(User user, String tokenSign, Long tokenOverTime) {
        if (user == null || user.getId() == null || user.getUserName() == null || user.getType() == null) {
            throw new ServiceException(TokenExceptionEnum.BODY_NOT_MATCH.getMessage());
        }
        return JWT.create()
                .withClaim("id", user.getId()) // 设置载荷
                .withClaim("username", user.getUserName())
                .withClaim("type", user.getType())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenOverTime)) // 设置令牌过期时间
                .sign(Algorithm.HMAC256(tokenSign));
    }

    /**
     * token令牌解析
     *
     * @param token 需要解析的令牌
     * @return 返回用户登录对象
     */
    public static User parseToken(String token, String tokenSign) {
        if (token == null || token.isEmpty()) {
            throw new ServiceException(TokenExceptionEnum.NOT_TOKEN.getMessage());
        }
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(tokenSign)).build().verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = new User();
            user.setId(claims.get("id").asInt());
            user.setUserName(claims.get("username").asString());
            user.setType(claims.get("type").asInt());
            return user;
        } catch (SignatureVerificationException e) {
            throw new ServiceException(TokenExceptionEnum.SIGNATURE_NOT_MATCH.getMessage());
        } catch (TokenExpiredException e) {
            throw new ServiceException(TokenExceptionEnum.TOKEN_OVER.getMessage());
        } catch (AlgorithmMismatchException e) {
            throw new ServiceException(TokenExceptionEnum.VISA_ALGORITHM.getMessage());
        } catch (Exception e) {
            throw new ServiceException(TokenExceptionEnum.LOGIN_ERROR.getMessage());
        }
    }
}
