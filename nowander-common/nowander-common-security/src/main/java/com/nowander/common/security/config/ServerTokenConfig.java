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
    /**
     * 获取 server-token 的 headerName
     */
    @NotEmpty
    private String headerName;
    /**
     * 缓存当前系统的 Credential 的 key
     */
    @NotEmpty
    private String cacheKey;
    /**
     * 当前系统的 serverId
     */
    @NotNull
    private Integer serverId;
    /**
     * 当前系统的 serverName
     */
    @NotEmpty
    private String serverName;
}
