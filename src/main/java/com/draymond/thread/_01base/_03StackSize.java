package com.draymond.thread._01base;

/**
 * stackSize
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/7 14:48
 */

/*
 构造Thread的时候，传入stackSize代表这该线程占用stack的大小，如果美亚由指定stackSize的大小，默认是0，0代表这会忽略该参数。该参数会被JNI函数（虚拟机函数）去使用
 需要注意：该参数有一些平台有效，一些无效
 */
public class _03StackSize {

    private static int counter = 1;

    public static void main(String[] args) {

        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter++;
                Thread thread=new Thread(()->{
                    byte[] bytes=new byte[1024*1024*2];

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        } catch (Exception e) {

        }

        System.out.println(counter);
    }

}