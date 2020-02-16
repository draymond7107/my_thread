package com.draymond.thread._01base;


/**
 * 守护线程
 * ----不能轻易使用:在非守护线程都结束后，守护线程也会结束；如果一些事情交给守护线程，则会造成该线程运行一半的时候中断
 * ----如果该线程需要在其他线程结束后强制释放资源，则可以使用守护线程
 * <p>
 * ---建立网络连接，使用守护线程（如果其他线程全部结束，网络连接的线程也需要关闭），如果不适用守护线程，其他的业务线程关闭了，该线程还可能在运行状态
 * <p>
 * 只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
 *
 * @author ZhangSuchao
 * @create 2019/7/24
 * @since 1.0.0
 */

/*
      使用范围：A向B发送心跳包，验证维护长连接，此线程设置为Daemon，其他的业务关闭的时候，此线程被强制关闭
 */
public class _06DaemonThread {

}

/*
    thread1为daemon线程，执行时间长；thread2为普通线程，执行时间短；thread2执行完后，没有active的普通线程，thread1也停止了执行
 */
class _06DaemonThread1 {
    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(15_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1为daemon线程");
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2为普通线程");
        });

        thread1.setDaemon(true);
        thread1.start();
        thread2.start();
    }
}

/*
    innerThread为daemon线程，outThread为普通线程，普通线程结束运行，daemon也会结束运行
    threadGroup2分组的线程，只有“innerThread为daemon的线程”，没有其他的active线程，然而还能执行输出，可见“daemon与ThreadGroup”没有关系
 */
class _06DaemonThread2 {

    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("分组1");
        ThreadGroup threadGroup2 = new ThreadGroup("分组2");

        Thread outThread = new Thread(threadGroup1, () -> {
            Thread innerThread = new Thread(threadGroup2, () -> {
                try {
                    Thread.sleep(10000);
                    System.out.println("innerThread为daemon线程");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            innerThread.setDaemon(true);
            innerThread.start();

            try {
                Thread.sleep(20000);
                System.out.println("outThread为普通线程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        outThread.start();
    }

}

//问题：daemon必须等所有的普通线程都停止后才会停止吗？还是只需要是同一个分组的
//     Daemon与ThreadGroup没有关系