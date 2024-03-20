package com.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-22:28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {
    private String host;
    private String username;
    private String password;
}
