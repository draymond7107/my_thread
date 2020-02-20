package com.draymond.thread._05design._01single;

/**
 * 单例模式：懒汉式加载
 * 缺点：当多个线程getInstance()，可能造成INSTANCE刚实例化一班，另一个线程执行了" if (SingleModel3.INSTANCE == null) "直接返回了未实例化完全的实例
 */
public class SingleModel3 {

    private int min;
    private int max;

    private static volatile SingleModel3 INSTANCE = null;

    private SingleModel3() {
        init();
    }

    //构造类的其他必要参数
    private void init() {
        min = 1;
        max = 10;
    }

    public static SingleModel3 getInstance() {

        if (SingleModel3.INSTANCE == null) {
            synchronized (SingleModel3.class) { //锁的位置不同，减少性能的浪费
                INSTANCE = new SingleModel3();
            }
        }
        return INSTANCE;
    }
}

