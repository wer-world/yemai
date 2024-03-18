package com.kgc.interceptors;

import com.kgc.entity.User;
import com.kgc.enums.ExceptionEnum;
import com.kgc.exception.LoginException;
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
    private ReplayUtil replayUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("LoginInterceptor preHandle start...");
        logger.debug("LoginInterceptor preHandle url:" + request.getRequestURI());
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String urlTime = request.getHeader("urlTime"); // 时间戳
        String random = request.getHeader("random"); // 随机数
        logger.debug("LoginInterceptor preHandle token:" + token);
        if (token == null || token.isEmpty()) {
            throw new LoginException(ExceptionEnum.NOT_TOKEN.getMessage());
        }
        if (urlTime == null || random == null || urlTime.isEmpty() || random.isEmpty()) {
            throw new LoginException(ExceptionEnum.ILLEGAL_REQUEST.getMessage());
        }
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        // 重放攻击校验
        // 时间戳校验
        boolean checkUrlTime = DateCheckUtil.checkDateTime(urlTime);
        // 随机数校验
        String checkRandom = replayUtil.checkRandom(random);
        if (!checkUrlTime || checkRandom == null) {
            throw new LoginException(ExceptionEnum.ILLEGAL_REQUEST.getMessage());
        }

        // 令牌校验
        String redisToken = operations.get(token);
        if (redisToken == null) {
            throw new LoginException(ExceptionEnum.TOKEN_OVER.getMessage());
        }
        User user = JWTUtil.parseToken(redisToken);
        user.setRandom(checkRandom);
        ThreadLocalUtil.set(user);
        logger.info("LoginInterceptor preHandle end...");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        User user = ThreadLocalUtil.get();
        replayUtil.removeRandom(user.getRandom());
        ThreadLocalUtil.remove();
    }
}
