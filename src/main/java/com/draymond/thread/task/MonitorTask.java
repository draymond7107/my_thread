package com.draymond.thread.task;

import com.draymond.thread.ThreadApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 钉钉消息监控任务
 */
@Component
public class MonitorTask implements Runnable {
    private static Log logger = LogFactory.getLog(MonitorTask.class);
    private final String TASK_NAME = "钉钉消息监控任务";
    private LinkedBlockingQueue<DingEvent> queue = new LinkedBlockingQueue<DingEvent>();
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void run() {
        while (true) {
            try {
                DingEvent event = get();
                if (event == null) continue;
                //logger.debug("执行钉钉事件>>>> "+JsonUtils.toStringNoEx(event));
                ConfigurableApplicationContext applicationContext = ThreadApplication.applicationContext;
                CallbackJob job = new CallbackJob();
                job.setMonitorTask(applicationContext.getBean("monitorTask", MonitorTask.class));   //从spring管理的bean中获取
                threadPoolTaskExecutor.execute(job);
                try {
                    Thread.sleep(500L);
                } catch (Exception e1) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(500L);
                } catch (Exception e1) {
                }
            }
        }
    }

    @PostConstruct
    public void start() {
        logger.debug("++++++++启动钉钉事件监控++++++++");
        Thread monitorThread = new Thread(this);
        monitorThread.setName(TASK_NAME);
        monitorThread.setUncaughtExceptionHandler((t, e) -> {
            logger.error(t.getName() + ":执行异常:" + e.getMessage());
        });
        monitorThread.start();
    }

    public void put(DingEvent t) throws InterruptedException {
        queue.put(t);
    }

    public DingEvent get() throws InterruptedException {
        return queue.take();
    }

    public DingEvent peek() {
        return queue.peek();
    }

    public boolean remove(DingEvent dingEvent) {
        return queue.remove(dingEvent);
    }

    public boolean contatin(DingEvent t) {
        Iterator<DingEvent> iterator = queue.iterator();
        while (iterator.hasNext()) {
            DingEvent dingEvent = iterator.next();
            if (dingEvent.equals(t)) return true;
        }
        return false;
    }

    public int getQueueSize() {
        return queue.size();
    }


}
