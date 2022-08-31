package com.nowander.common.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
     * 用户权限
     */
    Set<String> permissions;

    /**
     * 用户角色
     */
    Set<String> roles;

    /**
     * 登录时间
     */
    Long loginTime;


    /**
     * token 过期时间
     */
    Long expireAt;

    public UserCredential(Integer userId, String username, Set<String> permissions, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.permissions = permissions;
        this.roles = roles;
    }
}
