package com.nowander.gateway.infrastructure.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-09-30
 */
@Configuration
@EnableDiscoveryClient // nacos 服务注册与发现
public class ApplicationConfig {
}
