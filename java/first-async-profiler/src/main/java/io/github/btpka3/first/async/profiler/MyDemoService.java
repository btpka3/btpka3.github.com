package io.github.btpka3.first.async.profiler;

import lombok.SneakyThrows;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MyDemoService implements SmartLifecycle {

    AtomicBoolean started = new AtomicBoolean(false);
    AtomicBoolean needStop = new AtomicBoolean(false);
    Thread t = new Thread("aaa");

    @SneakyThrows
    public void sayHi() {
        Thread.sleep(1000);
        System.out.println("hi~ " + System.currentTimeMillis());
    }

    @Override
    public void start() {
        if (started.compareAndSet(false, true)) {
            t = new Thread(() -> {
                while (true) {
                    if (needStop.get()) {
                        break;
                    }
                    this.sayHi();
                }
            });
            t.start();
        }
    }

    @Override
    public void stop() {
        if (needStop.compareAndSet(false, true)) {
            t.interrupt();
        }
    }

    @Override
    public boolean isRunning() {
        return started.get();
    }
}