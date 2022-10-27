package com.nowander.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Getter
@Setter
public class UserCredentialMqConfig {

    @Value("${app.token.user.mq.topic}")
    private String tokenTopic;

    /**
     * token 失效
     */
    @Value("${app.token.user.mq.tags.invalid}")
    private String tokenInvalidTag;
}
