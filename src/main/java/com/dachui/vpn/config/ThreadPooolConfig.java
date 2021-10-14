package com.dachui.vpn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPooolConfig{

    @Bean(name = "bigDataHttpPool")
    public ThreadPoolTaskExecutor bigDataHttpPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        // cpu核心数
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(core*2 + 1);
        // 除核心线程外的线程存活时间
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(40);
        // 线程名称前缀
        executor.setThreadNamePrefix("bigdata-thread-execute");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


    @Bean(name = "statisticsPool")
    public ThreadPoolTaskExecutor statisticsPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        // cpu核心数
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(core*5 + 1);
        // 除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(30);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(80);
        // 线程名称前缀
        executor.setThreadNamePrefix("statistics-thread-execute");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


    @Bean(name = "smsPool")
    public ThreadPoolTaskExecutor smsPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        // 设置核心线程数
        executor.setCorePoolSize(1);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(3);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(40);
        // 线程名称前缀
        executor.setThreadNamePrefix("sms-thread-execute");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}