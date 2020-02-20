package com.draymond.thread._05design._02observer.observer01;

public abstract class Observer {


    public Observer(Subject subject) {
        subject.attach(this);  //再new Observer后，就直接把this添加到"监听组"中
    }

    public abstract void update() ;

}
