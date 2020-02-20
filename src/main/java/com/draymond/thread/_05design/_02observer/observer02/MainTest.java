package com.draymond.thread._05design._02observer.observer02;

public class MainTest {

    public static void main(String[] args) {
        SubjectImp subject = new SubjectImp();

        BinaryObserver binaryObserver = new BinaryObserver(subject);
        HexObserver hexObserver = new HexObserver(subject);
        subject.setValue(2);

    }
}
