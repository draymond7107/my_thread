package com.draymond.thread._05design._02observer.observer01;

public class BinaryObserver extends Observer {
    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {

        System.out.println("BinaryObserver");

    }
}
