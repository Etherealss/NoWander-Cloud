package com.nowander.account.infrasturcture.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2022-08-31
 */
@Configuration
@EnableFeignClients(basePackages = "com.nowander.account.infrasturcture.feign")
@EnableDiscoveryClient // nacos 服务注册与发现
public class ApplicationConfig {
}
