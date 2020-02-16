package com.draymond.thread.task;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * 钉钉回调任务,真正干活的类
 */
public class CallbackJob implements Runnable {
    private Log logger = LogFactory.getLog(CallbackJob.class);
    private DingEvent event;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private MonitorTask monitorTask;
    //相应钉钉回调时的值
    private static final String CALLBACK_RESPONSE_SUCCESS = "success";

    @Override
    public void run() {
    }

    @Resource
    public void setMonitorTask(MonitorTask monitorTask) {
        this.monitorTask = monitorTask;
    }
}
