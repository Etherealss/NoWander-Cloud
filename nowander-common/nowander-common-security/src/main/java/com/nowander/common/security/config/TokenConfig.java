package com.nowander.common.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("app.token")
@RefreshScope
@Getter
@Setter
public class TokenConfig {
    private String headerName;
    private String cacheKey;
}
