package com.draymond.thread._01base;

import org.junit.Test;

/**
 * interrupt 线程中断
 * 目的：告诉thread线程，应该停止执行
 * <p>
 * 当thread处于阻塞（sleep,join,wait）状态,并且thread被标记为interrupt状态，此时，thread会收到一个 InterruptedException异常；该线程可以接收异常做下一步处理
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/8 15:58
 */
public class _10Interrupt {


    /*
         thread处于阻塞状态 ×
         thread被中断       √
         thread捕获异常     ×

         线程收到中断信号    √
         线程停止           × (线程虽然收到中断信号，但是否停止是由本线程控制)

         线程打上中断标记，并且中断标记不会自动改为false
     */
    @Test
    public void test1() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(">>>" + "线程收到中断信号");
                }
                System.out.println(">>>" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        thread1.start();

        Thread.sleep(5000);
        thread1.interrupt();
        Thread.sleep(10000);
    }


    /*
         thread处于阻塞状态 √
         thread被中断       √
         thread捕获异常     √

         线程收到中断信号    √
         线程停止           × (线程虽然收到中断信号，但是否停止是由本线程控制)

         线程打上中断标记，并且捕获异常后线程标记又会改为false
     */
    @Test
    public void test2() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(">>>线程开始状态：" + Thread.currentThread().isInterrupted());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(">>>" + "线程收到中断信号");
                System.out.println(">>>线程中断状态" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
            System.out.println(">>>线程捕获中断异常后状态" + Thread.currentThread().isInterrupted());

        }, "t1");
        thread1.start();

        Thread.sleep(5000);
        thread1.interrupt();
        Thread.sleep(10000);
    }

}