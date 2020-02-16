package com.draymond.thread._03explicit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
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

    public synchronized void lock() {
        lock(0L);
    }

    public synchronized void lock(Long mills) {
        while (lock) {
            if (!threadCollection.contains(Thread.currentThread())) {
                threadCollection.add(Thread.currentThread());
            }
            System.out.println(Thread.currentThread().getName() + "::wait");
            try {
                this.wait(mills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock = true;
        threadCollection.remove(Thread.currentThread());
        curentThread = Thread.currentThread();
    }

    public synchronized boolean unLock() throws Exception {
        if (Thread.currentThread() == curentThread) {   //谁加的锁，谁释放
            lock = false;
            this.notifyAll();
            return true;
        }
        throw new Exception("释放锁权限异常");
    }

    public Collection waitThreadCollection() {
        return Collections.unmodifiableCollection(threadCollection);
    }

    public int waitThreadSize() {
        return threadCollection.size();
    }
}
