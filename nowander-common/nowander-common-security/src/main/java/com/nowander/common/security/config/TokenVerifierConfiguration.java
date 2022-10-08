package com.nowander.common.security.config;

import com.nowander.common.security.feign.UserTokenFeign;
import com.nowander.common.security.service.ITokenVerifier;
import com.nowander.common.security.service.RemoteTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @see ITokenVerifier
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TokenVerifierConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITokenVerifier tokenVerifier(UserTokenFeign userTokenFeign) {
        log.info("非 account 服务，使用 RemoteTokenVerifier 验证 Token");
        return new RemoteTokenVerifier(userTokenFeign);
    }
}
