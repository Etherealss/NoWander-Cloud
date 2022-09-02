package com.nowander.captcha.infrastructure.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Configuration
@ConfigurationProperties(prefix = "app.captcha")
@Validated
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaptchaConfiguration {
    @NotEmpty
    String cacheKey;

    @NotNull
    @Min(1)
    Integer timeoutSecond;

    @NotNull
    Integer charCount = 4;

    @Min(1)
    Integer width = 200;

    @Min(1)
    Integer height = 100;

    @Min(1)
    Integer lineCount = 150;
}
