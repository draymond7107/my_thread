package com.draymond.thread._03explicit;

public class LockTest {
    public static void main(String[] args) {

        Lock lock1 = new Lock();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                // 钩子程序
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.out.println("钩子程序：应用结束提醒");
                }));


                try {
                    lock1.lock(100L);
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "执行完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock1.unLock(); // 当"获取锁超时"时，调用该方法也会抛出“释放锁权限异常”(原因：1号thread没有获取到锁，
                        // 而2号thread获取到锁，此时，1号线程调用unlock时，CurrentThread是1号thread,儿currentThread被2号线程赋值。
                        // 造成两个thread不是同一个线程而抛出异常)
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }, i + "").start();


        }

//        try {
//            lock1.unLock();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
