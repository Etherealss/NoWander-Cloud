package com.nowander.common.security.config;

import com.nowander.common.security.feign.ServerCredentialFeign;
import com.nowander.common.security.service.auth.CredentialCacheHandler;
import com.nowander.common.security.service.auth.ICredentialVerify;
import com.nowander.common.security.service.auth.RemoteCredentialVerify;
import com.nowander.common.security.service.auth.server.IServerCredentialProvider;
import com.nowander.common.security.service.auth.server.RemoteIServerCredentialProvider;
import com.nowander.common.security.service.auth.server.ServerCredential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wtk
 * @date 2022-10-17
 */
@Configuration
@Slf4j
public class ServerCredentialConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IServerCredentialProvider serverCredentialProvider(
            ServerCredentialFeign serverCredentialFeign,
            ServerCredentialConfig config,
            RedisTemplate<String, ServerCredential> redisTemplate
    ) {
        log.info("非 auth 服务，使用 RemoteIServerCredentialProvider 生成 Token");
        return new RemoteIServerCredentialProvider(
                serverCredentialFeign,
                config,
                redisTemplate
        );
    }

    /**
     * WebMvcConfigurer -> Interceptor -> Feign 会导致循环依赖，需要使用 @Lazy 延迟加载
     * @param userTokenFeign
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ICredentialVerify remoteTokenVerifier(@Lazy CredentialCacheHandler userTokenFeign) {
        log.info("非 auth 服务，使用 RemoteTokenVerifier 验证 Token");
        return new RemoteCredentialVerify(userTokenFeign);
    }
}
