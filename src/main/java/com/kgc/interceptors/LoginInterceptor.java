package com.kgc.interceptors;

import com.kgc.config.TokenConfig;
import com.kgc.entity.User;
import com.kgc.enums.TokenExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.util.DateCheckUtil;
import com.kgc.util.JWTUtil;
import com.kgc.util.ReplayUtil;
import com.kgc.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录处理拦截器
 *
 * @Author: 魏小可
 * @Date: 2024-03-03-16:04
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("LoginInterceptor preHandle start...");
        if (tokenConfig.getIsToken()) {
            logger.debug("LoginInterceptor preHandle url:" + request.getRequestURI());
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            logger.debug("LoginInterceptor preHandle token:" + token);
            if (token == null || token.isEmpty()) {
                throw new ServiceException("LoginInterceptor preHandle " + TokenExceptionEnum.NOT_TOKEN.getMessage(), TokenExceptionEnum.NOT_TOKEN.getMsg());
            }
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            // 令牌校验
            String redisToken = operations.get(token);
            if (redisToken == null) {
                throw new ServiceException("LoginInterceptor preHandle " + TokenExceptionEnum.TOKEN_OVER.getMessage(), TokenExceptionEnum.TOKEN_OVER.getMsg());
            }
            User user = JWTUtil.parseToken(redisToken, tokenConfig.getTokenSign());
            user.setToken(token);
            ThreadLocalUtil.set(user);
        } else {
            // 配置默认属性
            User user = new User();
            user.setId(2);
            user.setLoginName("admin");
            user.setType(1);
            ThreadLocalUtil.set(user);
        }
        logger.info("LoginInterceptor preHandle end...");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
