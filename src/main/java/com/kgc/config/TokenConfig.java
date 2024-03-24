package com.kgc.config;

import com.kgc.enums.InitExceptionEnum;
import com.kgc.exception.InitException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
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
    private String tokenSign; // 令牌签名
    private Boolean isToken; // 是否开启令牌验证
    private Integer tokenOverHours; // 令牌超时时间(单位小时)
    private Long tokenTimeOut; // 令牌超时时间(单位毫秒)

    public void setTokenSign(String tokenSign) {
        if (tokenSign == null || tokenSign.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_TOKEN_SIGN_ERROR.getMessage(), "tokenSign", tokenSign);
        }
        this.tokenSign = tokenSign;
    }

    public void setIsToken(String isToken) {
        if (!"true".equals(isToken) && !"false".equals(isToken)) {
            throw new InitException(InitExceptionEnum.INIT_IS_TOKEN_ERROR.getMessage(), "isToken", isToken);
        }
        this.isToken = Boolean.valueOf(isToken);
    }

    public void setTokenOverHours(String tokenOverHours) {
        if (!NumberUtils.isCreatable(tokenOverHours)) {
            throw new InitException(InitExceptionEnum.INIT_TOKEN_OVER_HOURS_ERROR.getMessage(), "tokenOverHours", tokenOverHours);
        }
        this.tokenOverHours = Integer.valueOf(tokenOverHours);
        setTokenTimeOut();
    }

    private void setTokenTimeOut() {
        tokenTimeOut = (long) (tokenOverHours * 60 * 60 * 1000);
    }
}
