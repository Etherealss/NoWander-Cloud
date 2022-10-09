package com.nowander.favor.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Configuration
public class FavorConfig {
    @Value("${app.favor.record.cache.cache-key}")
    private String recordCacheKey;
    @Value("${app.favor.record.buffer.cache-key}")
    private String recordBufferKey;

    @Value("${app.favor.count.cache.cache-key}")
    private String countCacheKey;
    @Value("${app.favor.record.buffer.cache-key}")
    private String countBufferKey;
}
