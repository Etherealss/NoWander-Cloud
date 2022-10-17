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
 * 1. 服务与鉴权相关的配置
 * 2. 各个服务验证 serverToken 时，对结果的缓存的相关配置。缓存时长由 Credential 决定
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("app.token.server")
@RefreshScope
@Validated
@Getter
@Setter
public class ServerCredentialConfig {
    /**
     * 获取 server-token 的 headerName
     */
    @NotEmpty
    private String headerName;

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

    /**
     * 当前系统的 secret，在申请 serverToken 时使用
     */
    @NotEmpty
    private String secret;

    /**
     * 缓存当前系统的 Credential 的 key
     */
    @NotEmpty
    private String cacheKey;
}
