package me.test;

public class MyEventListenerImpl implements MyEventListener {

    public void handle(MyEvent event) {
        System.out.println("event handled.");
    }

}
