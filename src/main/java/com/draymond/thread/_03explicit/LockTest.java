package com.draymond.thread._03explicit;

public class LockTest {
    public static void main(String[] args) {

        Lock lock1 = new Lock();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    lock1.lock();
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lock1.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "执行完成");

            }, i+"").start();
        }

        try {
            lock1.unLock();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
