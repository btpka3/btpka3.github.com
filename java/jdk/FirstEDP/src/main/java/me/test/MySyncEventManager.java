package me.test;

import java.util.ArrayList;
import java.util.List;

public class MySyncEventManager {

    private List<MyEventListener> repository = new ArrayList<MyEventListener>();

    public void addEventListener(MyEventListener listener) {
        repository.add(listener);
    }

    public void removeEventListener(MyEventListener listener) {
        repository.remove(listener);
    }

    public void fire(MyEvent event) {
        for (MyEventListener l : repository) {
            l.handle(event);
        }
    }

}
