package com.draymond.thread._05design._02observer.observer01;

public class HexObserver extends Observer {
    public HexObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {

        System.out.println("HexObserver");

    }
}
