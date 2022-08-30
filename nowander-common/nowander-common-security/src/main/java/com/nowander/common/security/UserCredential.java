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
    Set<String> permissions;
    Set<String> roles;
    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * token 过期时间
     */
    private Long expireAt;
}
