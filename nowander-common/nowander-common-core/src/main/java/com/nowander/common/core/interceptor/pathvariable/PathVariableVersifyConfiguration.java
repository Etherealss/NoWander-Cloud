package com.nowander.common.core.interceptor.pathvariable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wtk
 * @date 2022-09-03
 */
@Configuration
@ConditionalOnProperty(prefix = "app.bean", name = "pathVariableVersify", havingValue = "true")
public class PathVariableVersifyConfiguration {

    @Bean
    public PathVariableVersifyInterceptor pathVariableVersifyInterceptor(
            List<PathVariableValidator> validatorList) {
        return new PathVariableVersifyInterceptor(validatorList);
    }

}
