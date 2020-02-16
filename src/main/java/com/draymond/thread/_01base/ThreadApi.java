package com.draymond.thread._01base;


/**
 * @author ZhangSuchao
 * @create 2019/7/24
 * @since 1.0.0
 */

public class ThreadApi {

}

/**
 * 线程优先级
 */
class _01_Priority {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 1000) {
                    System.out.println("-Index=" + i + "   name=" + Thread.currentThread().getName());
                    i++;
                }
            }
        };
        Thread thread1 = new Thread(runnable, "线程1");
        Thread thread2 = new Thread(runnable, "线程2");
        Thread thread3 = new Thread(runnable, "线程3");

        thread1.setPriority(Thread.MAX_PRIORITY);   //最高优先级
        thread2.setPriority(Thread.NORM_PRIORITY);  //中级优先级
        thread3.setPriority(Thread.MIN_PRIORITY);   //最低优先级
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

/**
 * join
 */
class _02_Join {

    public static void main(String[] args) {

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i < 10000) {
                    System.out.println("-Index=" + i + "   name=" + Thread.currentThread().getName());
                    i++;
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    thread1.join(1, 10);    //thread2先让thread1执行1毫秒

                    Thread.currentThread().join();  //此线程先等待此线程先运行完，进入到死循环
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 0;
                while (i < 100) {
                    System.out.println("-Index=" + i + "   name=" + Thread.currentThread().getName());
                    i++;
                }
            }
        };
        thread1.start();
        thread2.start();

    }

}

/**
 * 线程自身interrupt;;this.interrupt()。会将线程的运行标志interrupt的状态改为true。如果需要停止线程，需要捕获InterruptedException异常
 * （如果try-catch在循环体内，则只能终止的范围在本次循环；如果try-catch在循环体外，则终止该循环体的异常。总而言之：根据try-catch的作用范围终止运行的某些代码）
 * <p>
 * 1/2 阻塞中的中断
 */
//try在while中
class _03_Interrupt_sleep_01 {

    //会一直循环输出：true  线程在运行/false  线程在抛异常（原因：try-catch捕获的异常只作用到本次循环）
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().isInterrupted() + "  线程在运行");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.getMessage();
                        System.out.println(Thread.currentThread().isInterrupted() + "  线程在抛异常");
                    }
                    this.interrupt();
                }


            }
        };
        thread.start();


    }
}

//try包含while
class _03_Interrupt_sleep_02 {
    //只会运行一次：   false  线程在运行   /  true  线程在运行   /  false  线程在抛异常
    //原因：当线程sleep时，interrupt为true会引发InterruptedException异常，从try进入到catch，从而退出while循环

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println(Thread.currentThread().isInterrupted() + "  线程在运行");
                        Thread.sleep(100);
                        this.interrupt();   //自身打断
                    }
                } catch (InterruptedException e) {
                    e.getMessage();
                    System.out.println(Thread.currentThread().isInterrupted() + "  线程在抛异常");
                }
            }
        };
        thread.start();
        Thread.sleep(1000);
        thread.interrupt(); //外部打断
    }
}

//只有在线程block时，capture异常才会抛出InterruptException异常
// 1    / 2 /    false  /   true  /  3   /   4
class _03_Interrupt_sleep_03 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(1);
                    System.out.println(2);

                    System.out.println(Thread.currentThread().isInterrupted()); //false
                    this.interrupt();
                } catch (Exception e) {
                    e.getMessage();
                    return;
                }
                System.out.println(Thread.currentThread().isInterrupted());     //true(虽然中断标志为ture但是，线程没有block，因此线程不会停止)
                System.out.println(3);
                System.out.println(4);
            }
        };
        thread.start();

    }
}

/**
 * wait()阻塞：让thread进入等待状态，让线程释放其持有的锁，进入到monitor锁队列，等待其他线程调用该monitor的notify()/notifyAll()方法
 * 因为wait需释放锁，所以必须在synchronized中使用：没有锁时使用会抛出IllegalMonitorStateException（正在等待的对象没有锁）
 * notify也要在synchronized使用，应该指定对象，t1. notify()，通知t1对象的等待池里的线程使一个线程进入锁定池，然后与锁定池中的线程争夺锁。
 * 那么为什么要在synchronized使用呢？ t1. notify()需要通知一个等待池中的线程，那么这时我们必须得获得t1的监视器（需要使用synchronized），
 * 才能对其操作，t1. notify()程序只是知道要对t1操作，但是是否可以操作与是否可以获得t1锁关联的监视器有关。
 */
class _03_Interrupt_wait_04 {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
        thread.start();
        //先让main线程停止100秒，防止还没有thread线程还没有运行，main线程已经调用interrupt();;不能使用join()先让thread执行，否则interrupt()要在thread线程执行完后再执行，没有打断效果
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.isInterrupted()); //false
        thread.interrupt();
        System.out.println(thread.isInterrupted()); //true

    }

}

/**
 * notify(): MONITOR.notify()需要通知一个等待池中的线程，那么这时我们必须得获得 MONITOR 的监视器（需要使用synchronized），
 *           才能对其操作，MONITOR.notify()程序只是知道要对 thread1 操作，但是是否可以操作与是否可以获得 MONITOR 锁关联的监视器有关。
 */
class _03_Interrupt_znotify_05 {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    try {
                        System.out.println("线程开始等待");
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程结束等待");
                }
            }
        };
        thread1.start();

        Thread.sleep(1000); //简短的休眠

        // MONITOR.notify()需要通知一个等待池中的线程，那么这时我们必须得获得 MONITOR 的监视器（需要使用synchronized），
        // 才能对其操作，MONITOR.notify()程序只是知道要对 thread1 操作，但是是否可以操作与是否可以获得 MONITOR 锁关联的监视器有关。
        synchronized (MONITOR) {    //必须在synchronized中，不然获取不到锁
            MONITOR.notify();   //notify thread(必须使用MONITOR,才能notify该对象的等待池里的线程)：
        }
    }
}





