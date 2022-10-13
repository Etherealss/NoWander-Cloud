package com.nowander.common.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("app.token.server")
@RefreshScope
@Getter
@Setter
public class ServerTokenConfig {
    private String headerName;
    private String cacheKey;
    private UUID serverId;
    private String serverName;
}
