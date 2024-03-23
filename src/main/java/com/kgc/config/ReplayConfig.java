package com.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 重放攻击配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-23-10:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "replay")
public class ReplayConfig {
    private Integer maxRandom; // 最大储存的随机数
    private Integer minRandom; // 最小储存的随机数
    private Boolean isReplay; // 是否开启重放攻击
}
