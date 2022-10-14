package com.nowander.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@ConfigurationProperties("app.user-token")
@Getter
@Setter
public class UserTokenCacheConfig {
    private Long expireMs;
    private String cacheKey;
}
