package io.github.btpka3.nexus.clean.snapshot.service;

import io.reactivex.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

@Component
@Deprecated
public class RepoResSubscribe implements FlowableOnSubscribe {

//    public final BlockingDeque<RepoContentWithParent> q = new LinkedBlockingDeque<>();
//    AtomicReference<Throwable> errRef = new AtomicReference<>();
//    AtomicBoolean completed = new AtomicBoolean(false);
//
//
//    ExecutorService es = Executors.newCachedThreadPool();


//    @Autowired
//    Nexus2Api nexus2Api;

//    String repo;
//
//    String initPath;


    @Autowired
    private RepoResMonitor repoResMonitor;

    @Override
    public void subscribe(FlowableEmitter e) throws Exception {

        while (true) {

            Object lock = repoResMonitor.getLock();
            BlockingDeque<RepoContentEx> q = repoResMonitor.getQ();

            synchronized (lock) {
                lock.wait();
            }

            // 队列中有数据
            while (q.peekFirst() != null) {
                e.onNext(q.takeFirst());
            }

            // 出错了？
            AtomicReference<Throwable> errRef = repoResMonitor.getErrRef();
            if (errRef.get() != null) {
                e.onError(errRef.get());
                break;
            }

            // 已经结束？
            AtomicBoolean completed = repoResMonitor.getCompleted();
            if (completed.get()) {
                e.onComplete();
                break;
            }
        }
    }

}
