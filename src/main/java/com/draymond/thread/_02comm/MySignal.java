package com.draymond.thread._02comm;

/**
 * 通过共享对象通信
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/3 15:19
 */
/*
thread1 与 thread2 共享变量 isSync
 */
public class MySignal {
    protected boolean isSync = false;  //是否有锁

    public synchronized Boolean getSync() {
        return isSync;
    }

    public synchronized void setSync(Boolean isSync) {
        this.isSync = isSync;
    }
}

class MyRunnable implements Runnable {

    private MySignal mySignal;

    private MyRunnable() {
    }

    public MyRunnable(MySignal mySignal) {
        this.mySignal = mySignal;
    }


    @Override
    public void run() {
        while (true) {
            if (mySignal.isSync) continue;  //有锁，则不执行
            else {
                mySignal.setSync(true); //先获取锁，再执行
                System.out.println(Thread.currentThread().getName());
                int i = Thread.currentThread().getThreadGroup().activeCount();
                System.out.println(i);
                mySignal.setSync(false);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

class MySignalTest {

    public static void main(String[] args) {
        MySignal mySignal = new MySignal();

        Thread thread1 = new Thread(new MyRunnable(mySignal));
        Thread thread2 = new Thread(new MyRunnable(mySignal));
        thread1.start();
        thread2.start();
    }


}
