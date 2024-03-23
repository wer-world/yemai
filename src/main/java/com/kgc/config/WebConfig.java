package com.kgc.config;

import com.kgc.interceptors.LoginInterceptor;
import com.kgc.interceptors.ReplayInterceptors;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "web-config")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private ReplayInterceptors replayInterceptors;

    @Setter
    @Getter
    private String[] excludeToken;

    @Setter
    @Getter
    private String[] excludeReplay;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器开放登录与注册接口
        registry.addInterceptor(replayInterceptors).excludePathPatterns(excludeReplay);
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludeToken);
    }
}
