package com.kgc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 魏小可
 * @Date: 2024-03-20-22:11
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    @Setter
    private String tokenSign; // 令牌签名
    @Setter
    private Boolean isToken; // 是否开启令牌验证
    private Integer tokenOverHours; // 令牌超时时间(单位小时)
    private Long tokenTimeOut; // 令牌超时时间(单位毫秒)

    public void setTokenOverHours(Integer tokenOverHours) {
        this.tokenOverHours = tokenOverHours;
        setTokenTimeOut();
    }

    private void setTokenTimeOut() {
        tokenTimeOut = (long) (tokenOverHours * 60 * 60 * 1000);
    }
}
