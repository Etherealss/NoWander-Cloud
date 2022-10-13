package com.nowander.common.core.web;

import com.nowander.common.core.enums.BaseEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Configuration
public class EnumConverterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ConverterFactory.class)
    public ConverterFactory<?, BaseEnum> enumConverter() {
        return new IntegerEnumConverter();
    }
}
