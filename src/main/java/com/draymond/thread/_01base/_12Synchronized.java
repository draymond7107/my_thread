package com.draymond.thread._01base;

import org.junit.Test;

import java.io.IOException;

/**
 * 同步
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/17 13:31
 */
public class _12Synchronized {

    /*
        使加锁的代码串行化运行,参考_02BankLineUp
     */

    /**
     * this锁1
     */
    public synchronized void thisSync1() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thisSync1的this锁");

    }

    /**
     * this锁2
     */
    public synchronized void thisSync2() {

        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thisSync2的this锁");
    }

    /**
     * this锁演示结果：thisSync2 比 thisSync1 晚执行10秒
     */
    @Test
    public void thisSync() {

        _12Synchronized aSynchronized = new _12Synchronized();
        Thread thread1 = new Thread(() -> {
            aSynchronized.thisSync1();
        });

        Thread thread2 = new Thread(() -> {
            aSynchronized.thisSync2();
        });
        thread1.start();
        thread2.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //---------------------------------- class锁 -------------------------

    /**
     * class锁1
     */
    public synchronized static void classSync1() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("classSync1的class锁");
    }

    /**
     * class锁2
     */
    public synchronized static void classSync2() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("classSync2的class锁");
    }

    /**
     * class锁的存在：classSync1 比 classSync2 提前10秒执行(也可能是 classSync2 先抢到的)
     */
    @Test
    public void classSync() {
        Thread thread1 = new Thread(() -> {
            classSync1();
        });
        Thread thread2 = new Thread(() -> {
            classSync2();
        });

        thread1.start();
        thread2.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}