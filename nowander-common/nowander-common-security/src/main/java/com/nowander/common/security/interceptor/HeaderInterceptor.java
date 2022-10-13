package com.nowander.common.security.interceptor;

import com.nowander.common.core.interceptor.ConfigHandlerInterceptor;
import com.nowander.common.security.config.ServerTokenConfig;
import com.nowander.common.security.config.UserTokenConfig;
import com.nowander.common.security.exception.TokenException;
import com.nowander.common.security.service.auth.ITokenVerifier;
import com.nowander.common.security.service.auth.server.ServerCredential;
import com.nowander.common.security.service.auth.server.ServerSecurityContextHolder;
import com.nowander.common.security.service.auth.user.UserCredential;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.UUID;

/**
 * 获取 Token 并将相关信息存入 SecurityContextHolder 中
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderInterceptor implements ConfigHandlerInterceptor {

    private final ITokenVerifier tokenVerifier;
    private final UserTokenConfig userTokenConfig;
    private final ServerTokenConfig serverTokenConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        setUserToken(request);
        setServerToken(request);
        return true;
    }

    private void setUserToken(HttpServletRequest request) {
        String userToken = request.getHeader(userTokenConfig.getHeaderName());
        if (StringUtils.hasText(userToken)) {
            try {
                UserCredential userCredential = tokenVerifier.verifyToken(userToken, UserCredential.class);
                UserSecurityContextHolder.set(userCredential);
            } catch (TokenException ignored) {}
        }
    }

    private void setServerToken(HttpServletRequest request) {
        String serverToken = request.getHeader(serverTokenConfig.getHeaderName());
        if (StringUtils.hasText(serverToken)) {
            try {
                ServerCredential credential = tokenVerifier.verifyToken(serverToken, ServerCredential.class);
                ServerSecurityContextHolder.set(credential);
            } catch (TokenException ignored) {}
        } else {
            // TODO
            ServerCredential serverCredential = new ServerCredential();
            serverCredential.setServerId(UUID.fromString("e190cd82-7c7d-42b7-9944-01fca5bdc43e"));
            serverCredential.setServerName("nowander-forum");
            HashSet<UUID> set = new HashSet<>();
            set.add(UUID.fromString("ca5e077e-7d65-430d-ad45-f01559b59673"));
            serverCredential.setAccessibleServiceIds(set);
            ServerSecurityContextHolder.set(serverCredential);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserSecurityContextHolder.remove();
        ServerSecurityContextHolder.remove();
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
