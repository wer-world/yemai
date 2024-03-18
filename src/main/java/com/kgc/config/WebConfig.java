package com.kgc.config;

import com.kgc.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-03-16:13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludeStr = new String[]{
                "/user/login",
                "/user/register",
                "/replay/getRandom",
                "/category/**",
                "/error",
                "/**/*"
        };
        // 拦截器开放登录与注册接口
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludeStr);
    }
}
