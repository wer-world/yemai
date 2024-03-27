package com.kgc.interceptors;

import com.kgc.config.PermissionConfig;
import com.kgc.config.TokenConfig;
import com.kgc.entity.User;
import com.kgc.enums.TokenExceptionEnum;
import com.kgc.enums.UserExceptionEnum;
import com.kgc.exception.LoginException;
import com.kgc.util.JWTUtil;
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
 * 用户权限拦截器
 *
 * @Author: 魏小可
 * @Date: 2024-03-26-22:26
 */
@Component
public class UserPermissionInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PermissionConfig permissionConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("UserPermissionInterceptor preHandle start...");
        String uri = request.getRequestURI();
        logger.debug("UserPermissionInterceptor preHandle uri:" + uri);
        User user = ThreadLocalUtil.get();
        if (user.getType() >= permissionConfig.getOrPermission()) {
            // 做页面过滤
            for (String closePath : permissionConfig.getOrPermissionClose()) {
                if (uri.contains(closePath)) {
                    logger.info("UserPermissionInterceptor preHandle end...");
                    throw new LoginException("LoginInterceptor preHandle " + UserExceptionEnum.USER_PERMISSION_INSUFFICIENT.getMessage(), UserExceptionEnum.USER_PERMISSION_INSUFFICIENT.getMsg());
                }
            }
            for (String filterPath : permissionConfig.getOrPermissionOpen()) {
                if (uri.contains(filterPath)) {
                    logger.info("UserPermissionInterceptor preHandle end...");
                    return true;
                }
            }
        } else if (user.getType().equals(permissionConfig.getAdminPermission())) {
            // 做页面过滤
            for (String filterPath : permissionConfig.getAdminPermissionOpen()) {
                if (uri.contains(filterPath)) {
                    logger.info("UserPermissionInterceptor preHandle end...");
                    return true;
                }
            }
            throw new LoginException("LoginInterceptor preHandle " + UserExceptionEnum.USER_PERMISSION_INSUFFICIENT.getMessage(), UserExceptionEnum.USER_PERMISSION_INSUFFICIENT.getMsg());
        }
        logger.info("UserPermissionInterceptor preHandle end...");
        return true;
    }
}
