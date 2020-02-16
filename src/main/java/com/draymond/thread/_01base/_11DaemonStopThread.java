package com.draymond.thread._01base;

/**
 * 使用守护线程终止任务
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/14 11:43
 */
public class _11DaemonStopThread {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup1 =new ThreadGroup("g1") ;
        ThreadGroup threadGroup2 =new ThreadGroup("g2") ;
        Thread thread1 = new Thread(threadGroup1,() -> {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(threadGroup2,() -> {

            Thread thread3 = new Thread(threadGroup2,() -> {
                while (true) {
                    System.out.println("thread3线程一直在运行");
                }
            });
            thread3.setDaemon(true);        // thread3为守护线程
            thread3.start();

            while (!Thread.currentThread().isInterrupted()) {
            }
            System.out.println("thread2线程被打断");
        });


        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        thread2.interrupt();


    }
}