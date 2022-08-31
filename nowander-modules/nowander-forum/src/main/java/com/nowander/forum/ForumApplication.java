package com.nowander.forum;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wtk
 * @date 2022-08-29
 */
@MapperScan(value = "com.nowander.forum.domain", annotationClass = Mapper.class)
@SpringBootApplication
public class ForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
}