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
import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerPreAuthHandler implements IPreAuthHandler {
    private final ServerCredentialConfig serverCredentialConfig;
    private final CurServerCredentialHolder curServerCredentialHolder;
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
        Set<Integer> accessibleServiceIds = curServerCredentialHolder.get().getAccessibleServiceIds();
        boolean accessible = accessibleServiceIds.contains(requestServer.getServerId());
        if (!accessible) {
            String reason = String.format(
                    "请求认证不通过：请求方没有访问当前服务的权限。请求方id：%d，当前服务id：%d",
                    requestServer.getServerId(),
                    curServerCredentialHolder.get().getServerId()
            );
            log.debug(reason);
            throw new AuthenticationException(ApiInfo.NOT_PERMISSION, reason);
        }
    }
}
