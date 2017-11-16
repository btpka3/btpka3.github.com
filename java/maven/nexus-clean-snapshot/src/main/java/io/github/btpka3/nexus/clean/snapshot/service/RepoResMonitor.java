package io.github.btpka3.nexus.clean.snapshot.service;

import io.reactivex.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

@Deprecated
@Component
public class RepoResMonitor {
    public BlockingDeque<RepoContentEx> q = new LinkedBlockingDeque<>();
    public AtomicReference<Throwable> errRef = new AtomicReference<>();
    public AtomicBoolean completed = new AtomicBoolean(false);

    private ThreadPoolExecutor es;

    private final Object lock = new Object();

    Flowable f = Flowable.create((e) -> {

    }, BackpressureStrategy.BUFFER);

    public Object getLock() {
        return lock;
    }

    public ThreadPoolExecutor getEs() {
        return es;
    }

    public void setEs(ThreadPoolExecutor es) {
        this.es = es;
    }

    public BlockingDeque<RepoContentEx> getQ() {
        return q;
    }

    public void setQ(BlockingDeque<RepoContentEx> q) {
        this.q = q;
    }

    public AtomicReference<Throwable> getErrRef() {
        return errRef;
    }

    public void setErrRef(AtomicReference<Throwable> errRef) {
        this.errRef = errRef;
    }

    public AtomicBoolean getCompleted() {
        return completed;
    }

    public void setCompleted(AtomicBoolean completed) {
        this.completed = completed;
    }
}
