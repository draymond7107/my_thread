package com.draymond.thread._05design.single;

/**
 * 单例模式：懒汉式加载
 * 获取实例的时候才会创建对象
 * 优点：解决了对象过早的实例化问题，避免内存浪费
 * 缺点：每次获取实例化的时候因为synchronized产生性能问题
 */
public class SingleModel2 {

    //volatile关键字：保证内存的可见性；避免了指令重排问题，造成实例化未结束的时候，其他线程调用getInstance方法造成的实例的INSTANCE是不完全的，从而造成空指针异常
    private volatile static SingleModel2 INSTANCE = null;

    private SingleModel2() {
    }

    public synchronized SingleModel2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingleModel2();
        }
        return SingleModel2.INSTANCE;
    }

}
