package com.nowander.common.security.service.auth.user;

import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.common.security.annotation.RequiresPermissions;
import com.nowander.common.security.annotation.RequiresRoles;
import com.nowander.common.security.service.auth.IPreAuthHandler;
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
    private final LogicAuthService logicAuthService;

    @Override
    public boolean checkNeedAuth(Method method) {
        if (method.getAnnotation(AnonymousAccess.class) == null
                || method.getAnnotation(InternalAuth.class) == null) {
            log.info("匿名访问接口，无需检验");
            return false;
        }
        return true;
    }

    @Override
    public void doAuth(Method method) {
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        try {
            logicAuthService.requireToken();

            // 校验 @RequiresRoles 注解
            if (requiresRoles != null) {
                logicAuthService.checkRole(requiresRoles);
            }

            // 校验 @RequiresPermissions 注解
            if (requiresPermissions != null) {
                logicAuthService.checkPermi(requiresPermissions);
            }
        } catch (AuthenticationException e) {
            log.debug("请求认证不通过：{}", e.getMessage());
            throw e;
        }
    }
}
