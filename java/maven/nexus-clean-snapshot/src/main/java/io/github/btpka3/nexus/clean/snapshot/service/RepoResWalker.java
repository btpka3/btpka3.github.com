package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import io.reactivex.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.concurrent.atomic.*;

@Component
@Scope(scopeName = "prototype")
@Deprecated
public class RepoResWalker implements Runnable {

//    BlockingDeque<RepoContentWithParent> q = new LinkedBlockingDeque<>();
//    AtomicReference<Throwable> errRef = new AtomicReference<>();
//    AtomicBoolean completed = new AtomicBoolean(false);

    @Autowired
    private RepoResMonitor repoResMonitor;

    @Autowired
    private Nexus2Api nexus2Api;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${nexus.repo}")
    private String repo;

    private String path;


    /**
     * 检查当前 path 下面是否有 非 leaf 资源，有的话，就挑选处理，提交的 ExecutorService 等待处理。
     */
    private void checkSubRes(RepoContentEx p) {

        if (p == null || p.getContent() == null || p.getContent().getData() == null) {
            return;
        }


        for (RepoContent.Item item : p.getContent().getData()) {
            if (!item.isLeaf()) {

                RepoResWalker w = applicationContext.getBean(RepoResWalker.class);
                w.setPath(item.getRelativePath());
                repoResMonitor.getEs().submit(w);
            }
        }


    }


    void d() {
        AtomicReference<FlowableEmitter> emitterRef = new AtomicReference<>();

        Flowable.create((e) -> {
            emitterRef.set(e);
            //e.onNext();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public void run() {


        String _path = path;
        if (!StringUtils.hasText(_path)) {
            _path = "/";
        }

        Object lock = repoResMonitor.getLock();

        nexus2Api.listRepoContent(repo, _path)
                .addCallback(
                        c -> {
                            RepoContentEx p = new RepoContentEx();
                            //p.setCurRelativePath(path);
                            p.setContent(c.getBody());
                            repoResMonitor.getQ().add(p);

                            synchronized (lock) {
                                lock.notify();
                            }
                            checkSubRes(p);

                        },
                        err -> {
                            repoResMonitor.getErrRef().set(err);
                            synchronized (lock) {
                                lock.notify();
                            }
                        }
                );

    }


    public Nexus2Api getNexus2Api() {
        return nexus2Api;
    }

    public void setNexus2Api(Nexus2Api nexus2Api) {
        this.nexus2Api = nexus2Api;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
