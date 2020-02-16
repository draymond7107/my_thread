package com.draymond.thread._01base;


/**
 *对线程加锁
 * 问题：为什么此线程没有加上锁？thread1没有执行完，thread2也可以执行？
 *  每个线程执行完
 * @author ZhangSuchao
 * @create 2019/5/21
 * @since 1.0.0
 */

public class ThreadSynchronized extends Thread {
    private static Integer ticket = 10;

    @Override
    public void run() {
        System.out.println("run运行");
        synchronized (this) {
            try {
                String name = Thread.currentThread().getName();

                while (this.ticket > 1) {
                    Thread.sleep(1000);
                    ticket--;
                    System.out.println(name + " ::::" + ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadSynchronized myThread1 =new ThreadSynchronized();
        ThreadSynchronized myThread2 =new ThreadSynchronized();
        ThreadSynchronized myThread3 =new ThreadSynchronized();
        myThread1.start();
        myThread2.start();
        myThread3.start();
    }

}
