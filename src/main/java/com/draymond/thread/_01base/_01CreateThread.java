package com.draymond.thread._01base;

import org.junit.Test;

/**
 * @Auther: ZhangSuchao
 * @Date: 2020/1/2 16:37
 */
public class _01CreateThread {

    /**
     * newThread 方式
     */
    @Test
    public void newThreadWay() {

        Thread thread = new Thread("myThread") {
            @Override
            public void run() {
                System.out.println(" new Thread()方式实现");
            }
        };
        thread.start();
        System.out.println(thread.getName());
    }

    /**
     * newRunnable 方式
     */
    @Test
    public void newRunnableWay() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("new Runnable()方式实现");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }


    /**
     * newRunnable+ lambda 方式是实现
     */
    @Test
    public void runnableLambdaWay() {
        Runnable runnable = () -> {
            System.out.println("new Runnable()+ lambda 方式是实现");
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * new Thread()+runnable+lambda 方式
     */
    @Test
    public void threadRunnableLambdaWay() {
        Thread thread = new Thread(() -> {
            System.out.println(" new Thread()+runnable+lambda 方式实现");
        });
        thread.setName("最简洁的实现");
        thread.start();
    }


}