package com.draymond.thread._03explicit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

/**
 * 自定义锁
 * <p>
 * 实现加锁，解锁功能
 * 多个线程共同抢一个锁，等待的线程放到队列中
 */
public class Lock {
    private Boolean lock = false;
    private Collection<Thread> threadCollection = new ArrayList<>();
    private Thread curentThread;    //curentThread在lock中赋值，因为lock有锁，因此赋值的操作是安全的，不会出现其他线程在本线程没结束完再次赋值的问题

    public Lock() {
        lock = false;
    }


    public synchronized void lock() throws InterruptedException {
        while (lock) {
            if (!threadCollection.contains(Thread.currentThread())) {
                threadCollection.add(Thread.currentThread());
            }
            System.out.println(Thread.currentThread().getName() + "::wait");
            this.wait();
        }
        lock = true;
        threadCollection.remove(Thread.currentThread());
        curentThread = Thread.currentThread();
    }

    /**
     * 加锁：等待mills毫秒，若还没等到，则抛出超时异常
     *
     * @param mills
     * @throws InterruptedException
     */
    public synchronized void lock(Long mills) throws InterruptedException, TimeoutException {
        if (mills <= 0) lock();
        Long endTime = System.currentTimeMillis() + mills;  //最终的等待时间

        while (lock) {
            // 判断超时
            long currentTime = System.currentTimeMillis();
            if (currentTime > endTime) {
                throw new TimeoutException(Thread.currentThread().getName() + "线程获取锁超时");
            }

            if (!threadCollection.contains(Thread.currentThread())) {
                threadCollection.add(Thread.currentThread());
            }
            System.out.println(Thread.currentThread().getName() + "::wait");
            this.wait(mills);
        }
        lock = true;
        threadCollection.remove(Thread.currentThread());
        curentThread = Thread.currentThread();
    }

    public synchronized void unLock() throws Exception {
        Thread thread = Thread.currentThread();
        Thread thread1 = curentThread;
        if (thread == thread1) {   //谁加的锁，谁释放
            lock = false;
            this.notifyAll();
        } else {
            throw new Exception(Thread.currentThread().getName() + "释放锁权限异常");
        }
    }

    public Collection waitThreadCollection() {
        return Collections.unmodifiableCollection(threadCollection);
    }

    public int waitThreadSize() {
        return threadCollection.size();
    }
}
