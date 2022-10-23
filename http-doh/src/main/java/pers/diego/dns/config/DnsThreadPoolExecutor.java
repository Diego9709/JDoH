package pers.diego.dns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kang.zhang
 * @date 2021/11/26 19:42
 */

@Component
public class DnsThreadPoolExecutor{

    @Bean
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置线程池核心容量
        executor.setCorePoolSize(Integer.MAX_VALUE);
        // 设置线程池最大容量
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        // 设置任务队列长度
        executor.setQueueCapacity(500);
        // 设置线程超时时间
        executor.setKeepAliveSeconds(30);
        // 设置任务丢弃后的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
