package me.test;

import java.util.EventListener;

public interface MyEventListener extends EventListener {

    void handle(MyEvent event);

}
