package com.nowander.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Getter
@Setter
public class UserCredentialCacheConfig implements ICredentialCacheConfig {
    @Value("${app.user.token.expire-ms}")
    private Long tokenExpireMs;
    @Value("${app.user.token.cache-key}")
    private String tokenCacheKey;

    @Value("${app.user.refresh-token.expire-ms}")
    private Long refreshTokenExpireMs;
    @Value("${app.user.refresh-token.cache-key}")
    private String refreshTokenCacheKey;
}
