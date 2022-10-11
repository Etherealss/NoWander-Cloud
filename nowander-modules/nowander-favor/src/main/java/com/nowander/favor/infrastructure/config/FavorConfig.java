package com.nowander.favor.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Getter
@Setter
@Configuration
public class FavorConfig {
    @Value("${app.favor.record.cache.cache-key}")
    private String recordCacheKey;
    @Value("${app.favor.record.cache.expire-ms}")
    private Long recordCacheExpireMs;
    @Value("${app.favor.record.buffer.cache-key}")
    private String recordBufferKey;

    @Value("${app.favor.count.cache.cache-key}")
    private String countCacheKey;
    @Value("${app.favor.count.cache.expire-ms}")
    private Long countCacheExpireMs;
    @Value("${app.favor.count.buffer.cache-key}")
    private String countBufferKey;
}
