package com.kgc.config;

import com.kgc.enums.InitExceptionEnum;
import com.kgc.exception.InitException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 易买网初始化配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-23-10:25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "easy-buy-init")
public class EasyBuyInitConfig {
    private Boolean initEsData; // 开启es初始化
    private Boolean initReplay; // 开启重放攻击随机数初始化
}
