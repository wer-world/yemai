package com.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 文件相关配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-22:24
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileConfig {
    private String filePath = new File("").getAbsolutePath() + File.separator + "images" + File.separator;
}
