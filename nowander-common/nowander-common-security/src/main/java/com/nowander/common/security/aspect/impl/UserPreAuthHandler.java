package com.nowander.common.security.aspect.impl;

import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.annotation.RequiresPermissions;
import com.nowander.common.security.annotation.RequiresRoles;
import com.nowander.common.security.aspect.IPreAuthHandler;
import com.nowander.common.security.service.auth.user.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wtk
 * @date 2022-10-07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserPreAuthHandler implements IPreAuthHandler {
    private final UserAuthService userAuthService;

    @Override
    public boolean checkNeedAuth(Method method) {
        AnonymousAccess anonymousAccess = method.getAnnotation(AnonymousAccess.class);
        if (anonymousAccess == null) {
            return true;
        }
        log.debug("匿名访问接口，无需检验");
        return false;
    }

    @Override
    public void doAuth(Method method) {
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        try {
            userAuthService.requireToken();

            // 校验 @RequiresRoles 注解
            if (requiresRoles != null) {
                userAuthService.checkRole(requiresRoles);
            }

            // 校验 @RequiresPermissions 注解
            if (requiresPermissions != null) {
                userAuthService.checkPermi(requiresPermissions);
            }
        } catch (AuthenticationException e) {
            log.debug("请求认证不通过：{}", e.getMessage());
            throw e;
        }
    }
}
