package com.draymond.thread._01base;

import org.junit.Test;

import java.io.IOException;

/**
 * 线程优先级：越大优先级越高
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/7 17:59
 */
public class _08Priority {

    @Test
    public void test1() throws IOException {
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        }, "t1");

        Thread thread2 = new Thread(() -> {
            for (int i = 1; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        }, "t2");

        Thread thread3 = new Thread(() -> {
            for (int i = 1; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        }, "t3");

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);
        thread1.start();
        thread2.start();
        thread3.start();
        System.in.read();
    }
}