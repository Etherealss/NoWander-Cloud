package com.nowander.common.security.config;

import com.nowander.common.security.feign.UserTokenFeign;
import com.nowander.common.security.service.token.ITokenVerifier;
import com.nowander.common.security.service.token.RemoteTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @see ITokenVerifier
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TokenVerifierConfiguration {

    /**
     * WebMvcConfigurer -> Interceptor -> Feign 会导致循环依赖，需要使用 @Lazy 延迟加载
     * @param userTokenFeign
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ITokenVerifier remoteTokenVerifier(@Lazy UserTokenFeign userTokenFeign) {
        log.info("非 account 服务，使用 RemoteTokenVerifier 验证 Token");
        return new RemoteTokenVerifier(userTokenFeign);
    }
}
