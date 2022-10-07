package com.nowander.common.security.aspect;

import com.nowander.common.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-10-07
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class PreAuthHandlerAutoConfiguration {

    private final AuthService authService;

    @Bean
    @ConditionalOnMissingBean
    public IPreAuthHandler preAuthHandler() {
        return new DefaultPreAuthHandler(authService);
    }
}
