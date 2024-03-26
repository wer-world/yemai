package com.kgc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 用户类
 *
 * @Author: 魏小可
 * @Date: 2024-03-17-21:05
 */
@Data
@ToString
public class User {
    private Integer id;
    private String loginName;
    private String userName;
    @JsonIgnore
    private String password;
    private Integer sex;
    private String identityCode;
    private String email;
    private String mobile;
    private String address;
    private Integer type;
    @JsonIgnore
    private String random;
    private String token;
}
