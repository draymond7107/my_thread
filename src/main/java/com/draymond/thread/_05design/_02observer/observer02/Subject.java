package com.draymond.thread._05design._02observer.observer02;

/**
 * 主题：
 */
public interface Subject {


    void attach(Observer observer);

    void detach(Observer observer);

    void notifyAllObserver();

}
