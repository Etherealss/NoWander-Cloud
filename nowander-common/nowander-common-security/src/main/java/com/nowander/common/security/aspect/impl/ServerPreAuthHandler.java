package com.nowander.common.security.aspect.impl;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.common.security.aspect.IPreAuthHandler;
import com.nowander.common.security.config.ServerTokenConfig;
import com.nowander.common.security.service.auth.server.ServerCredential;
import com.nowander.common.security.service.auth.server.ServerSecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerPreAuthHandler implements IPreAuthHandler {
    private ServerTokenConfig serverTokenConfig;
    @Override
    public boolean checkNeedAuth(Method method) {
        InternalAuth internalAuth = method.getAnnotation(InternalAuth.class);
        if (internalAuth == null) {
            return true;
        }
        log.debug("非服务内部请求，无需检验");
        return false;
    }

    @Override
    public void doAuth(Method method) {
        ServerCredential requestServer = ServerSecurityContextHolder.require();
        UUID curServerId = serverTokenConfig.getServerId();
        boolean accessible = requestServer.getAccessibleServiceIds().contains(curServerId);
        if (!accessible) {
            String reason = MessageFormat.format(
                    "请求认证不通过：请求方没有访问当前服务的权限。请求方id：{}，当前服务id：{}",
                    requestServer.getServerId(),
                    curServerId
            );
            log.debug(reason);
            throw new AuthenticationException(ApiInfo.NOT_PERMISSION, reason);
        }
    }
}
