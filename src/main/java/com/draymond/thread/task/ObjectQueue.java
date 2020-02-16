package com.draymond.thread.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 功能说明：临时用一下,后续用队列处理<br>
 * 模块名称：<br>
 * 功能描述：<br>
 * 文件名称: br>
 * 系统名称：ICELOVE<br>
 * 软件著作权：ICELOVE 版权所有<br>
 * 开发人员：lujun <br>
 * 开发时间：2019/8/12 18:20<br>
 * 系统版本：1.0.0<br>
 */
public class ObjectQueue extends ConcurrentLinkedQueue<Object> {
    private static Log logger = LogFactory.getLog(ObjectQueue.class);
    private static final long serialVersionUID = 1L;
    private Object lock;



    public ObjectQueue(Object lock) {
        this.lock = lock;
    }
    @Override
    public boolean add(Object queueItem) {
        boolean flag = super.add(queueItem);
        synchronized (lock) {
            lock.notify();
        }
        return flag;
    }

}
