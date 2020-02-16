package com.draymond.queue;


import com.draymond.thread.task.MonitorTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZhangSuchao
 * @create 2019/8/21
 * @since 1.0.0
 */

public class LinkedBlockingQueueDemo {
    private static Log logger = LogFactory.getLog(LinkedBlockingQueueDemo.class);
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();

    public static void main(String[] args) {
        LinkedBlockingQueueDemo demo = new LinkedBlockingQueueDemo();
        demo.queue.add("111");
        demo.queue.add("222");
        demo.queue.add("333");
        String peek = demo.queue.peek();
        if (!StringUtils.isEmpty(peek)) {
            try {
                System.out.println(peek);
                Iterator<String> iterator = demo.queue.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    System.out.println("去除一个元素的队列：" + next);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                demo.queue.remove(peek);
                Iterator<String> iterator = demo.queue.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    System.out.println("剩余的队列：" + next);
                }
            }
        }


    }

}
