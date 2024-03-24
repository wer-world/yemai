package com.kgc.config;

import com.kgc.enums.InitExceptionEnum;
import com.kgc.exception.InitException;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-20-22:28
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {
    private String host;
    private String username;
    private String password;

    public void setHost(String host) {
        if (host == null || host.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_EMAIL_HOST_ERROR.getMessage(), "host", host);
        }
        this.host = host;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_EMAIL_USERNAME_ERROR.getMessage(), "username", username);
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_EMAIL_PASSWORD_ERROR.getMessage(), "password", password);
        }
        this.password = password;
    }
}
