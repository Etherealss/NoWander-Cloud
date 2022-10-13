package com.nowander.common.security.service.auth.server;

import cn.hutool.extra.spring.SpringUtil;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.ServletUtil;
import com.nowander.common.security.config.ServerTokenConfig;
import com.nowander.common.security.exception.TokenException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 服务间鉴权
 * @author wtk
 * @date 2022-08-30
 */
public class ServerSecurityContextHolder {
    private static final ThreadLocal<ServerCredential> CREDENTIALS = new InheritableThreadLocal<>();
    private static final ServerTokenConfig TOKEN_CONFIG = SpringUtil.getBean(ServerTokenConfig.class);

    public static void set(ServerCredential credential) {
        Objects.requireNonNull(credential.getServerId());
        Objects.requireNonNull(credential.getServerName());
        Objects.requireNonNull(credential.getToken());
        Objects.requireNonNull(credential.getRefreshToken());
        Objects.requireNonNull(credential.getAccessibleServiceIds());
        CREDENTIALS.set(credential);
    }

    @Nullable
    public static ServerCredential get() {
        return CREDENTIALS.get();
    }

    @NonNull
    public static ServerCredential require() {
        ServerCredential credential = CREDENTIALS.get();
        if (credential == null) {
            String token = ServletUtil.getRequest().getHeader(TOKEN_CONFIG.getHeaderName());
            if (!StringUtils.hasText(token)) {
                throw new TokenException(ApiInfo.SERVER_TOKEN_MISSING);
            } else {
                throw new TokenException(ApiInfo.SERVER_TOKEN_INVALID);
            }
        }
        return credential;
    }

    public static void remove() {
        CREDENTIALS.remove();
    }


}
