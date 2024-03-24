package com.kgc.config;

import com.kgc.enums.InitExceptionEnum;
import com.kgc.exception.InitException;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 易买网初始化配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-23-10:25
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "easy-buy-init")
public class EasyBuyInitConfig {
    private Boolean initEsData; // 开启es初始化
    private Boolean initReplay; // 开启重放攻击随机数初始化

    public void setInitEsData(String initEsData) {
        if (!"true".equals(initEsData) && !"false".equals(initEsData)) {
            throw new InitException(InitExceptionEnum.INIT_ES_DATA_ERROR.getMessage(), "initEsData", initEsData);
        }
        this.initEsData = Boolean.valueOf(initEsData);
    }

    public void setInitReplay(String initReplay) {
        if (!"true".equals(initReplay) && !"false".equals(initReplay)) {
            throw new InitException(InitExceptionEnum.INIT_REPLAY_ERROR.getMessage(), "initReplay", initReplay);
        }
        this.initReplay = Boolean.valueOf(initReplay);
    }
}
