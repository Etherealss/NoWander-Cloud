package com.nowander.auth;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wtk
 * @date 2022-10-14
 */
@ComponentScan("com.nowander")
@MapperScan(value = "com.nowander.auth.domain", annotationClass = Mapper.class)
@EnableFeignClients(basePackages = "com.nowander")
@EnableDiscoveryClient // nacos 服务注册与发现
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
