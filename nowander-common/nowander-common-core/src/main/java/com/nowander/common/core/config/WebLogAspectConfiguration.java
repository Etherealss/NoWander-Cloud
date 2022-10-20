package com.nowander.common.core.config;

import com.nowander.common.core.web.WebLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-10-20
 */
@Configuration
@ConditionalOnProperty(prefix = "app.common", name = "web-log", havingValue = "true")
public class WebLogAspectConfiguration {
    @Bean
    public WebLogAspect webLogAspect() {
        return new WebLogAspect();
    }
}
