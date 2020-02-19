package com.draymond.thread.other;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * waitSet
 * 通过JVM虚拟机实现的，调用wait方法，会将此线程放入到wait set中，然后放弃锁(对某些资源的声明)
 * 1：所有的对象都会有一个wait set,用来存放调用了该对象wait方法之后进入block状态线程
 * 2：线程被notify之后，并一定立即执行
 * 3:线程被唤醒顺序不固定
 * 4：线程被唤醒后，必须重新获取锁(该线程执行到的"位置"是被记录下来的，而不是从 synchronized (LOCK) 开始再次执行一遍)
 */
public class _01WaitSet {

    private final static Object LOCK = new Object();
/*
    每个类都有一个waitSet，存放wait的线程。

    his method causes the current thread (call it <var>T</var>) to place itself in the wait set for this object
    and then to relinquish any and all synchronization claims on this object.
    他的方法导致当前线程(调用它<var>T</var>)将自己置于此对象的 wait set 中，然后放弃此对象上的任何和所有同步声明。
 */

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(i ->
                new Thread(() -> {
                    synchronized (LOCK) {
                        try {
                            Optional.of(Thread.currentThread().getName() + "  will come to wait set").ifPresent(System.out::println);
                            LOCK.wait();
                            Optional.of(Thread.currentThread().getName() + "  will leave to wait set").ifPresent(System.out::println);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (LOCK) {
            IntStream.rangeClosed(1, 10).forEach(i -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOCK.notify();
            });

        }

    }


    public static void work() {
        System.out.println("加锁前执行");
        synchronized (LOCK) {
            System.out.println("抢到锁后执行");

            try {
                System.out.println("wait前执行");
                LOCK.wait();
                System.out.println("wait后执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 4：线程被唤醒后，必须重新获取锁(该线程执行到的"位置"是被记录下来的，而不是从 synchronized (LOCK) 开始再次执行一遍)
     */
    @Test
    public void test1() throws InterruptedException {

        new Thread(() -> work()).start();

        Thread.sleep(3000);
        synchronized (LOCK) {
            LOCK.notify();
        }
        Thread.sleep(10000);

    }
}
