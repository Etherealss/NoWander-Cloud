package com.nowander.common.security;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.ServletUtil;
import com.nowander.common.security.config.TokenConfig;
import com.nowander.common.security.exception.TokenException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 暂存当前线程中的用户权限信息
 * @author wtk
 * @date 2022-08-30
 */
public class SecurityContextHolder {
    private static final ThreadLocal<UserCredential> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void set(UserCredential userCredential) {
        Objects.requireNonNull(userCredential.getUserId());
        Objects.requireNonNull(userCredential.getUsername());
        Objects.requireNonNull(userCredential.getToken());
        Objects.requireNonNull(userCredential.getRefreshToken());
        Objects.requireNonNull(userCredential.getPermissions());
        Objects.requireNonNull(userCredential.getRoles());
        THREAD_LOCAL.set(userCredential);
    }

    @Nullable
    public static UserCredential get() {
        return THREAD_LOCAL.get();
    }

    @NonNull
    public static UserCredential require() {
        UserCredential userCredential = THREAD_LOCAL.get();
        if (userCredential == null) {
            String token = ServletUtil.getRequest().getHeader(TokenConfig.HEADER_TOKEN);
            if (!StringUtils.hasText(token)) {
                throw new TokenException(ApiInfo.TOKEN_MISSING);
            } else {
                throw new TokenException(ApiInfo.TOKEN_INVALID);
            }
        }
        return userCredential;
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
