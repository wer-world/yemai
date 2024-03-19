package com.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝接口调用配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-16:41
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    private String appId;
    private String openApi;
    private String publicKey;
    private String privateKey;
    private String aliPublicKey;
    private String notifyUrl;
    private String returnUrl;
}
