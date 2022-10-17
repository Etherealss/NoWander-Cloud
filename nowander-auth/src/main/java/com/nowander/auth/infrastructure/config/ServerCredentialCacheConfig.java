package com.nowander.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 认证服务创建的 serverToken 的缓存相关配置
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Getter
@Setter
public class ServerCredentialCacheConfig implements ICredentialCacheConfig{

    @Value("${app.auth.server.token.expire-ms}")
    private Long tokenExpireMs;
    @Value("${app.auth.server.token.cache-key}")
    private String tokenCacheKey;
    
    @Value("${app.auth.server.refresh-token.expire-ms}")
    private Long refreshTokenExpireMs;
    @Value("${app.auth.server.refresh-token.cache-key}")
    private String refreshTokenCacheKey;


}
