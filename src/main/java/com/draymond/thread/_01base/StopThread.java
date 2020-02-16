package com.draymond.thread._01base;


/**
 * 停止线程
 * `````1：线程阻塞时，使用interrupt(参照ThreadApi)
 * `````2:执行体中判断：当interrupt为true时，不再执行
 * `````3，任务线程设置为Daemon线程
 *
 * @author ZhangSuchao
 * @create 2019/8/1
 * @since 1.0.0
 */

public class StopThread {
}

/**
 * 优雅的停止线程
 * ``````执行体中判断：当interrupt为true时，不再执行
 */
class ElegantStopThread {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            int i = 1;

            @Override
            public void run() {
                while (!Thread.interrupted() && i < 10000000) {         //线程被interrupt时，不再执行
                    System.out.println("执行业务" + i);
                    i++;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(1000);
        thread.interrupt(); //打断线程
    }
}

class FlagElegantStopThread {

    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            int i = 1;

            @Override
            public void run() {
                while (!flag) {         //线程被interrupt时，不再执行
                    System.out.println("执行业务" + i);
                    i++;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(1000);
        // thread.interrupt(); //打断线程
        stopThread();
    }

    public static void stopThread() {
        flag = true;
    }
}


/**
 * 暴力终止线程
 * ``````将任务线程设置为daemon线程（user线程全部终止时，daemon线程也会终止）
 * ``````手动打断“user线程”
 */
class ViolentStopThread {
    //将执行任务的线程设置为Daemon线程
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Runnable executeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        ViolentStopThread violentStopThread = new ViolentStopThread();
                        violentStopThread.execute();
                    }
                };
                Thread executeThread = new Thread(executeRunnable);
                executeThread.setDaemon(true);
                executeThread.start();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(100);
        System.out.println("main线程执行完毕");
        thread.interrupt();
    }

    //执行业务的方法
    private void execute() {
        int i = 1;
        while (true) {
            System.out.println("执行业务 " + i);
            i++;
        }
    }
}

/**
 * 只要同组的线程没有user线程，daemon线程就会终止
 */
class ViolentStopThread1 {
    //将执行任务的线程设置为Daemon线程
    public static void main(String[] args) throws InterruptedException {

        //任务线程：设置为daemon，执行任务
        Runnable executeRunnable = new Runnable() {
            @Override
            public void run() {
                ViolentStopThread1 violentStopThread1 = new ViolentStopThread1();
                violentStopThread1.execute();
            }
        };
        Thread thread = new Thread(executeRunnable);

        //终止线程的标记线程
        Runnable userRunnable = new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                }
            }
        };
        ThreadGroup threadGroup = new ThreadGroup("fffff");
        Thread thread1 = new Thread(threadGroup, userRunnable);
        thread.setDaemon(true);
        thread.start();
        thread1.start();

        Thread.sleep(100);
        System.out.println("main线程执行完毕");
        thread1.interrupt();
    }

    //执行业务的方法
    private void execute() {
        int i = 1;
        while (true) {
            System.out.println("执行业务 " + i);
            i++;
        }
    }
}

