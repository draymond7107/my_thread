package com.draymond.thread.task;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 功能说明：cn.pertech.tongmtx.schedule<br>
 * 模块名称：<br>
 * 功能描述：<br>
 * 文件名称: br>
 * 系统名称：ICELOVE<br>
 * 软件著作权：ICELOVE 版权所有<br>
 * 开发人员：lujun <br>
 * 开发时间：2019/7/15 13:19<br>
 * 系统版本：1.0.0<br>
 */
@Component
@Data
public class ThreadPoolFactory {
    private Log logger = LogFactory.getLog(getClass());
    @Value("${thread-pool-factory.corePoolSize}")
    private int corePoolSize;
    @Value("${thread-pool-factory.maxPoolSize}")
    private int maxPoolSize;
    @Value("${thread-pool-factory.queueCapacity}")
    private int queueCapacity;
    @Value("${thread-pool-factory.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        logger.info("创建线程池");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //加入此头后此线程池成为系统线程池
        threadPoolTaskExecutor.setThreadNamePrefix("Anno-Executor");
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setRejectedExecutionHandler((r, executor) -> {
        });
        return threadPoolTaskExecutor;
    }
}
