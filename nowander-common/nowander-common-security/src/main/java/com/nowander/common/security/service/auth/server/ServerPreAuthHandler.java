package com.nowander.common.security.service.auth.server;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.common.security.config.ServerCredentialConfig;
import com.nowander.common.security.service.auth.IPreAuthHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerPreAuthHandler implements IPreAuthHandler {
    private final ServerCredentialConfig serverCredentialConfig;
    @Override
    public boolean checkNeedAuth(Method method) {
        InternalAuth internalAuth = method.getAnnotation(InternalAuth.class);
        if (internalAuth == null) {
            log.info("非服务内部请求，无需检验");
            return false;
        }
        return true;
    }

    @Override
    public void doAuth(Method method) {
        ServerCredential requestServer = ServerSecurityContextHolder.require();
        Integer curServerId = serverCredentialConfig.getServerId();
        boolean accessible = requestServer.getAccessibleServiceIds().contains(curServerId);
        if (!accessible) {
            String reason = String.format(
                    "请求认证不通过：请求方没有访问当前服务的权限。请求方id：%s，当前服务id：%s",
                    requestServer.getServerId().toString(),
                    curServerId.toString()
            );
            log.debug(reason);
            throw new AuthenticationException(ApiInfo.NOT_PERMISSION, reason);
        }
    }
}
