package com.nowander.common.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredential {

    Integer userId;

    String username;

    String token;

    String refreshToken;

    /**
     * token 过期时间
     */
    Date tokenExpireAt;

    /**
     * refreshToken 过期时间
     */
    Date refreshTokenExpireAt;

    /**
     * 登录时间
     */
    Date loginTime;

    /**
     * 用户权限
     */
    Set<String> permissions;

    /**
     * 用户角色
     */
    Set<String> roles;

    public UserCredential(Integer userId, String username, Set<String> permissions, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.permissions = permissions;
        this.roles = roles;
    }
}
