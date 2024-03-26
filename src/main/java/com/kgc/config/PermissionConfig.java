package com.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限配置类
 *
 * @Author: 魏小可
 * @Date: 2024-03-26-22:28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "permission")
public class PermissionConfig {
    private Integer orPermission; // 普通用户权限
    private Integer adminPermission; // 管理员权限
    private Integer superAdminPermission; // 超级管理员权限
    private String[] orPermissionOpen; // 普通成员开放接口
    private String[] orPermissionClose; // 普通用户关闭接口
    private String[] adminPermissionOpen; // 管理员开放接口
}
