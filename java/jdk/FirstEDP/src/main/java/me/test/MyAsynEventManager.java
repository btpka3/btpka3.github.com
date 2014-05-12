package me.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyAsynEventManager implements Runnable {

    private List<MyEventListener> repository = new ArrayList<MyEventListener>();

    private BlockingQueue<MyEvent> eventQueue = new LinkedBlockingQueue<MyEvent>(1000);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10,
            TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

    public void addEventListener(MyEventListener listener) {
        repository.add(listener);
    }

    public void removeEventListener(MyEventListener listener) {
        repository.remove(listener);
    }

    public void fire(MyEvent event) {
        try {
            eventQueue.put(event);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void run() {
        while (true) {
            MyEvent event;
            try {
                event = eventQueue.take();
                for (MyEventListener listener : repository) {
                    executor.execute(new MyListenerExecutor(listener, event));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class MyListenerExecutor implements Runnable {

        private MyEventListener listener;
        private MyEvent event;
        public MyListenerExecutor(MyEventListener listener, MyEvent event) {
            this.listener = listener;
            this.event = event;
        }
        public MyEventListener getListener() {
            return this.listener;
        }

        public MyEvent getEvent() {
            return event;
        }
        public void run() {
            listener.handle(event);

        }
    }
}
