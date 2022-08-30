package com.nowander.common.security;

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

    public static UserCredential get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
