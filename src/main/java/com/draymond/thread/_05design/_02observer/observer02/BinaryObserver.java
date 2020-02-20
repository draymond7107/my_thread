package com.draymond.thread._05design._02observer.observer02;

public class BinaryObserver implements Observer {
    private BinaryObserver() {
    }

    public BinaryObserver(Subject subject) {
        subject.attach(this);
    }

    @Override
    public void sendMsg(String msg) {
        System.out.println("接到通知：BinaryObserver " + msg);
    }
}
