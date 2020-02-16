package com.draymond.thread;


/**
 * 线程打断
 *
 * @author ZhangSuchao
 * @create 2019/8/19
 * @since 1.0.0
 */

public class InterRuptThread {
    public static void main(String[] args) throws InterruptedException {
//############################################   第一种 ###########################################
        //打断线程
        Runnable runnable = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("非interrupt状态");
            }
            System.out.println("interrupt状态，线程结束");
        };

        Thread thread = new Thread(runnable


                
        );
        thread.start();
        //外部打断
        Thread.sleep(100L);
        thread.interrupt();
    }


}

//############################################   第二种 ###########################################
class InterRuptThread1 implements Runnable {

    private boolean isInterrupt = false;

    @Override
    public void run() {
        while (!isInterrupt) {
            System.out.println("非interrupt状态");
        }
        System.out.println("interrupt状态，线程结束");
    }

    private void stopThread() {
        this.isInterrupt = true;
    }

    public static void main(String[] args) throws InterruptedException {
        InterRuptThread1 interRuptThread1 = new InterRuptThread1();
        Thread thread = new Thread(interRuptThread1);
        thread.start();
        Thread.sleep(1000L);
        interRuptThread1.stopThread();
    }
}
