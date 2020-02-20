package com.draymond.thread._05design._02observer.observer01;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject（目标或主题）
 * 它是指被观察的对象。我们在主题中定义一个观察者集合。一个观察者对象可以接收任意多个观察者。同时提供了一系列的方法管理这些观察者。
 * 比如attach添加观察者到集合中，detach从集合中剔除观察者。notify通知集合中的所有观察者。
 */
public class Subject {
    // 被检测的值(该值变化，则告知其他方法)
    private int value;

    //监听组：监测value的对象(value变化时，需要主动调用Observer中的方法)
    List<Observer> observerList = new ArrayList<>();

    public int getValue() {
        return value;
    }

    /**
     * 当value的值改变时，告知所有的观察者
     *
     * @param value
     */
    public void setValue(int value) {

        if (value != this.value) {
            notifyAllObserver();
        }
        this.value = value;
    }

    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 通知所有的观察者
     */
    public void notifyAllObserver() {
        observerList.forEach(Observer::update);
    }

    public static void main(String[] args) {
        Subject subject = new Subject();
        new HexObserver(subject);
        new BinaryObserver(subject);
        subject.setValue(1);

    }

}
