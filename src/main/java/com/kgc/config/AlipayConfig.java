package com.kgc.config;

import com.kgc.enums.InitExceptionEnum;
import com.kgc.exception.InitException;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝接口调用配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-16:41
 */
@Getter
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

    public void setAppId(String appId) {
        if (appId == null || appId.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_APPID_ERROR.getMessage(), "appId", appId);
        }
        this.appId = appId;
    }

    public void setOpenApi(String openApi) {
        if (openApi == null || openApi.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_OPENAPI_ERROR.getMessage(), "openApi", openApi);
        }
        this.openApi = openApi;
    }

    public void setPublicKey(String publicKey) {
        if (publicKey == null || publicKey.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_PUBLIC_KEY_ERROR.getMessage(), "publicKey", publicKey);
        }
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        if (privateKey == null || privateKey.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_PRIVATE_KEY_ERROR.getMessage(), "privateKey", privateKey);
        }
        this.privateKey = privateKey;
    }

    public void setAliPublicKey(String aliPublicKey) {
        if (aliPublicKey == null || aliPublicKey.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_ALI_PUBLIC_KEY_ERROR.getMessage(), "aliPublicKey", aliPublicKey);
        }
        this.aliPublicKey = aliPublicKey;
    }

    public void setNotifyUrl(String notifyUrl) {
        if (notifyUrl == null || notifyUrl.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_NOTIFY_URL_ERROR.getMessage(), "notifyUrl", notifyUrl);
        }
        this.notifyUrl = notifyUrl;
    }

    public void setReturnUrl(String returnUrl) {
        if (returnUrl == null || returnUrl.isEmpty()) {
            throw new InitException(InitExceptionEnum.INIT_RETURN_URL_ERROR.getMessage(), "returnUrl", returnUrl);
        }
        this.returnUrl = returnUrl;
    }
}
