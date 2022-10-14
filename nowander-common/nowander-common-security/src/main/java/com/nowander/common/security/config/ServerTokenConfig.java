package com.nowander.common.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("app.token.server")
@RefreshScope
@Validated
@Getter
@Setter
public class ServerTokenConfig {
    @NotEmpty
    private String headerName;
    @NotEmpty
    private String cacheKey;
    @NotNull
    private Integer serverId;
    @NotEmpty
    private String serverName;
}
