package com.nowander.account.infrasturcture.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@ConfigurationProperties("app.user-refresh-token")
@Validated
@Setter
@Getter
public class UserRefreshTokenCacheConfig {
    @NotNull
    private Long expireMs;
    @NotEmpty
    private String cacheKey;
}
