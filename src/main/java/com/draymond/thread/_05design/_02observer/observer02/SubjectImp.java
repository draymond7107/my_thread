package com.draymond.thread._05design._02observer.observer02;

import java.util.ArrayList;
import java.util.List;

public class SubjectImp implements Subject {

    private int value;
    private List<Observer> observerList = new ArrayList<>();


    public SubjectImp() {
        this.value = 0;
    }
    public SubjectImp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 修改value值：修改后的值不同后，会向所有观察者发送通知
     *
     * @param value
     */
    public void setValue(int value) {
        if (this.value != value) {
            notifyAllObserver();
        }
        this.value = value;
    }

    @Override
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyAllObserver() {
        observerList.stream().forEach(observer -> observer.sendMsg("value now is " + value));
    }
}
