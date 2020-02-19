package com.draymond.thread._05design.single;

/**
 * 静态内部类模式
 */
public class SingleModel4 {

    private static class InstanceHolder {
        private final static SingleModel4 singleModel4 = new SingleModel4();
    }

    public SingleModel4 getInstance() {
        return InstanceHolder.singleModel4;
    }

}
