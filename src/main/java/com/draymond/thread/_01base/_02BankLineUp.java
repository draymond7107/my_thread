package com.draymond.thread._01base;

import org.junit.Test;

import java.io.IOException;

/**
 * 银行排队叫号的线程
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/4 15:35
 */
public class _02BankLineUp {
    private int minNum = 1;
    private int maxNum = 1000;
    private int sum = 0;
    private Object lock = new Object();

    /**
     * 会发生线程安全问题
     *
     * @return
     */
    public Runnable getRunnable() {
        Runnable runnable = () -> {
            while (minNum <= maxNum) {
                String name = Thread.currentThread().getName();
                System.out.println("线程名字" + name + "  排队号" + minNum);
                minNum++;
            }
        };
        return runnable;
    }

    /**
     * 发生线程安全问题
     *
     * @return
     */
    public Runnable getRunnable3() {
        Runnable runnable = () -> {
            while (minNum <= maxNum) {      //minNum == maxNum时， 3个线程都执行到此处，thread1获取锁，thread2,thread3等待；
                                            // 等到thread1完释放锁，因为另外的来给你个线程都是通过了while的判断，所以还会继续执行，发生线程安全问题
                synchronized (lock) {
                    sum = minNum + sum;
                    minNum++;
                }
            }
            System.out.println(sum);
        };
        return runnable;
    }

    /**
     * 不会有线程安全问题
     *
     * @return
     */
    public Runnable getRunnable4() {
        Runnable runnable = () -> {
            while (true) {
                synchronized (lock) {           //先获取锁，再执行 minNum <= maxNum 判断，确保 if 条件只有单线程执行，避免了线程安全
                    if (minNum <= maxNum) {
                        sum = minNum + sum;
                        minNum++;
                    } else {
                        break;
                    }

                    try {
                        Thread.sleep(100-000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(sum);
        };
        return runnable;
    }


    /**
     * 加锁的情况
     *
     * @return
     */
    public Runnable getRunnable1() {
        Runnable runnable = () -> {
            while (true) {
                synchronized (lock) {
                    if (minNum <= maxNum) {
                        System.out.println("线程名字" + Thread.currentThread().getName() + "  排队号" + minNum);
                        minNum++;
                    } else {
                        break;
                    }
                }
            }
        };
        return runnable;
    }

    @Test
    public void test1() {
        Thread thread1 = new Thread(getRunnable4(), "线程1");
        Thread thread2 = new Thread(getRunnable4(), "线程2");
        Thread thread3 = new Thread(getRunnable4(), "线程3");
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Thread thread1 = new Thread(getRunnable(), "线程1");
        Thread thread2 = new Thread(getRunnable(), "线程2");
        Thread thread3 = new Thread(getRunnable(), "线程3");
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 2020/1/7 线程安全的问题
    //不加锁为什么没造成"超卖"现象

    // synchronized 使线程串行化运行


}