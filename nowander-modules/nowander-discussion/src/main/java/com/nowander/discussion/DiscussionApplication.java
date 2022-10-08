package com.nowander.discussion;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wtk
 * @date 2022-09-06
 */
@ComponentScan("com.nowander")
@MapperScan(value = "com.nowander.discussion.domain", annotationClass = Mapper.class)
@EnableDiscoveryClient
@EnableFeignClients("com.nowander")
@SpringBootApplication
public class DiscussionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscussionApplication.class, args);
    }
}