package com.nowander.common.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wang tengkun
 * @date 2022/3/1
 */
public class SecurityUtil {

    public static UserCredential getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserCredential) {
            return (UserCredential) principal;
        }
        return null;
    }
}
