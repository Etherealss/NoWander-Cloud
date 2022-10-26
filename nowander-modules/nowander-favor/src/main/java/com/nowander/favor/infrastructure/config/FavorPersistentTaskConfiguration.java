package com.nowander.favor.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * SpringTask 多线程支持
 * 参考：https://juejin.cn/post/7043598815986712590#heading-6
 * @author wtk
 * @date 2022-10-20
 */
@EnableScheduling
@Configuration
public class FavorPersistentTaskConfiguration {
    /**
     * 初始化了一个线程池大小为 5  的 TaskScheduler, 避免了所有任务都用一个线程来执行
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("nowander-favor-task-scheduler");
        return taskScheduler;
    }
}
