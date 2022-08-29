package com.nowander.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wtk
 * @date 2022-08-29
 */
@ComponentScan("com.nowander.forum.domain")
@SpringBootApplication()
public class ForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
}