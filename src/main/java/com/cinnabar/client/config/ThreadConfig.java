package com.cinnabar.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
//    @Bean
//    public ScheduledThreadPoolExecutor scheduledExecutorService() {
//        return new ScheduledThreadPoolExecutor(10);
//    }
}
