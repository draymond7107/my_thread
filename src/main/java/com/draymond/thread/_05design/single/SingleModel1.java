package com.draymond.thread._05design.single;

/**
 * 单例模式:饿汉式
 *          类加载的时候就完成了实例化
 *      优点：没有线程的安全的问题
 *      缺点：类的实例没有使用的时候就会创建，占用内存
 */
public class SingleModel1 {

    private SingleModel1() {
    }

    private final static SingleModel1 INSTANCE = new SingleModel1();

    public static SingleModel1 getInstance() {
        return INSTANCE;
    }
}
