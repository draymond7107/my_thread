package com.draymond.thread._02comm;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 通过 wait notify通信
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/3 15:24
 */

/*
    向集合中添加元素，当size=0等待，add时 notifyAll
 */
public class MyWait {


    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();

    public void add(String str) {
        queue.add(str);
        queue.notifyAll();
    }

    public int getSize() {
        return queue.size();
    }

    public LinkedBlockingQueue<String> getQueue() {
        return queue;
    }


    public static void main(String[] args) {
        MyWait myWait = new MyWait();


        Thread thread = new Thread(() -> {
            while (true) {
                if (myWait.getSize() == 0) {
                    try {
                        myWait.getQueue().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    String poll = myWait.getQueue().poll();
                    System.out.println(poll);
                }
            }

        });

        thread.start();
        int i = 100;
        int m = 0;
        while (m < i) {
            myWait.add("qwe");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m++;
        }

    }


}