package com.draymond.thread._01base;

import org.junit.Test;

/**
 * Join 当前线程等待join的线程执行后执行
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/7 18:02
 */
public class _09Join {

    /**
     * test线程等待thread1线程执行完毕后再执行
     *
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        }, "t1");

        thread1.start();
        thread1.join();
        //   thread1.join(1000);
        //   thread1.join(10000,100);
        System.out.println("main线程执行结束");
        System.out.println(Thread.currentThread().getName() + "执行完毕");  //main执行完毕 test线程也是开启的main线程
    }


    /**
     * 当前线程等待当前线程结束，造成一直不结束
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        Thread.currentThread().join();
    }
}