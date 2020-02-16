package com.draymond.thread._01base;

/**
 *  ThreadGroup 对线程分组
 * @Auther: ZhangSuchao
 * @Date: 2020/1/2 20:35
 */
public class _05ThreadGroup {

    /*
        没有传ThreadGroup，则使用父的ThreadGroups
     */
    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        String name = thread.getThreadGroup().getName();
        System.out.println(name);

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int activeCount = threadGroup.activeCount();
        System.out.println(activeCount);  // 3
        Thread[] threads = new Thread[activeCount];
        threadGroup.enumerate(threads);
        for (Thread thread1 : threads) {
            String name1 = thread1.getName();
            System.out.print("name::"+name1+"        ");
            boolean daemon = thread1.isDaemon();
            System.out.println(daemon);
        }
        /*
                        3
                name::main        false
                name::Monitor Ctrl-Break        true
                name::Thread-0        false
         */

    }



}