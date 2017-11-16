package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import io.reactivex.*;
import io.reactivex.parallel.*;
import io.reactivex.schedulers.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.regex.*;

@Service
public class CleanSnapshotService {

    @Autowired
    private RepoResSubscribe repoResSubscribe;

    @Autowired
    Nexus2Api nexus2Api;

    @Value("${nexus.repo}")
    String repo;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${nexus.maxSnapshotCount:5}")
    Integer maxSnapshotCount;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean isSnapshotVersionDir(String path) {
        return path.endsWith("-SNAPSHOT/");
    }

    public static void main(String[] args) {
//        CleanSnapshotService s = new CleanSnapshotService();
//        ArtifactInfo i = s.toSnapshotArtifactInfo("/net/kingsilk/qh-activity-api/0.0.1-SNAPSHOT/");
//        System.out.println(i);

        String pattern = "(qh-activity-api"
                + "-"
                + "0.0.1".replaceAll("\\.", "\\\\.")
                + "-\\d+{8}\\.\\d{6}-"
                + "(\\d+))\\D.+";
//
//        +".*";

        System.out.println(pattern);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher("qh-activity-api-0.0.1-20171109.031527-80.jar.sha1");
        System.out.println(m.matches());
        System.out.println(m.group(1));
        System.out.println(m.group(2));

    }

    public ArtifactInfo toSnapshotArtifactInfo(String relativePath) {

        String p = relativePath;
        if (p.startsWith("/")) {
            p = p.substring(1);
        }
        if (p.endsWith("/")) {
            p = p.substring(0, p.length() - 1);
        }

        String[] arr = p.split("/");
        ArtifactInfo info = new ArtifactInfo();
        info.setGroupId(String.join(".", Arrays.copyOfRange(arr, 0, arr.length - 2)));
        info.setArtifactId(arr[arr.length - 2]);
        String version = arr[arr.length - 1];
        info.setVersion(version);
        info.setVersionNumber(version.replace("-SNAPSHOT", ""));
        return info;
    }

    public void del(RepoContent content, ArtifactInfo info) {

        String patternStr = "("
                + info.getArtifactId()
                + "-"
                + info.getVersionNumber().replaceAll("\\.", "\\\\.")
                + "-\\d+{8}\\.\\d{6}-"
                + "(\\d+)"
                + ")"
                + "\\D.+";


        Pattern p = Pattern.compile(patternStr);

        content.getData().stream().filter(i -> i.isLeaf())
                .map(i -> {
                    Matcher m = p.matcher(i.getText());
                    if (m.matches()) {
                        return Arrays.asList(i.getText(), m.group(1), m.group(2));
                    } else {
                        return null;
                    }
                })
                .filter(i -> i != null)

        ;
    }


    public static void listFiles(String dir) {
        System.out.println("----------------------------------- listFiles");
        BlockingDeque<File> q = new LinkedBlockingDeque<>();
        q.add(new java.io.File(dir));

        while (q.peekFirst() != null) {
            java.io.File f = q.poll();

            if (!f.exists()) {
                continue;
            }

            System.out.println(f.getAbsolutePath());
            if (f.isDirectory()) {
                Arrays.stream(f.listFiles()).forEach(q::add);
            }
        }
        System.out.println("done.");
    }


    /**
     * 检查当前 path 下面是否有 非 leaf 资源，有的话，就挑选处理，提交的 ExecutorService 等待处理。
     */
    private void checkSubRes(RepoContentEx p, FlowableEmitter<RepoResDirJobConf> emitter) {

        if (p == null || p.getContent() == null || p.getContent().getData() == null) {
            return;
        }

        for (RepoContent.Item item : p.getContent().getData()) {
            if (!item.isLeaf()) {
                emitter.onNext(createConf(item.getRelativePath(), emitter));
            }
        }
    }

    private RepoResDirJobConf createConf(String path, FlowableEmitter<RepoResDirJobConf> e) {
        RepoResDirJobConf conf = new RepoResDirJobConf();
        conf.setApplicationContext(applicationContext);
//        conf.setSuccessCallback(c -> {
//            checkSubRes(c, e);
//        });
//        conf.setErrorCallback(err -> {
//            e.onError(err);
//            runningJob.remove(conf);
//        });
        conf.setRepo(repo);
        conf.setPath(path);
        return conf;
    }


    private Set<RepoResDirJobConf> runningJob = new LinkedHashSet<>();


    public ParallelFlowable<RepoContentEx> createRepoContentExFlow() {
        AtomicReference<FlowableEmitter<RepoResDirJobConf>> jobConfEmitterRef = new AtomicReference<>();

        // RepoResDirJobConf 流
        return Flowable.<RepoResDirJobConf>create(

                e -> {
                    jobConfEmitterRef.set(e);
                    e.onNext(createConf("/net/kingsilk/qh-agency/", e));
                },
                BackpressureStrategy.BUFFER
        )
                .parallel()
                .runOn(Schedulers.computation())
                .flatMap(conf -> {

                    // 通过 Nexus2 的 REST API 异步查询。
                    //    - 通过 RepoResDirJobConf 的回调，提交子目录的查询任务，实现递归查询

                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {
                        contentEx.getContent().getData().stream()
                                .filter(item -> !item.isLeaf())
                                .forEach(item -> {
                                    logger.debug("RepoResDirJobConf 流 : emmit : onNext : " + item);
                                    jobConfEmitterRef.get().onNext(createConf(item.getRelativePath(), null));
                                });
                    }));

                    //    - 通过 下面额外的回调处理，使得 能确定 何时 RepoResDirJobConf 流 何时已经终止
                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {
                        runningJob.remove(conf);
                        if (runningJob.isEmpty()) {
                            logger.debug("RepoResDirJobConf 流 : emmit : onComplete");
                            jobConfEmitterRef.get().onComplete();
                        }
                    }));
                    conf.setErrorCallback(conf.getErrorCallback().andThen(err -> {
                        runningJob.remove(conf);
                        if (runningJob.isEmpty()) {
                            logger.debug("RepoResDirJobConf 流 : emmit : onError : " + err);
                            jobConfEmitterRef.get().onError(err);
                        }
                    }));

                    //    - 通过 下面额外的 success 处理，使当前事件流能继续向后、异步地变成 RepoContentEx 流
                    AtomicReference<FlowableEmitter<RepoContentEx>> emitterRef = new AtomicReference<>();
                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {

                        logger.debug("RepoContentEx 流 : emmit : onNext : " + contentEx);
                        emitterRef.get().onNext(contentEx);

                        logger.debug("RepoContentEx 流 : emmit : onComplete");
                        emitterRef.get().onComplete();
                    }));

                    RepoResQueryJob job = new RepoResQueryJob();
                    runningJob.add(conf);
                    job.accept(conf);

                    return Flowable.create(
                            emitterRef::set,
                            BackpressureStrategy.BUFFER
                    );
                });
    }


    public void clean(Consumer onComplete, Consumer onError) {


//        RepoResQueryJob rootJob = applicationContext.getBean(RepoResQueryJob.class);

//        AtomicReference<FlowableEmitter<RepoResDirJobConf>> jobConfEmitterRef = new AtomicReference<>();
//
//        // RepoResDirJobConf 流
//        Flowable.<RepoResDirJobConf>create(
//
//                e -> {
//                    jobConfEmitterRef.set(e);
//                    e.onNext(createConf("/net/kingsilk/qh-agency/", e));
//                },
//                BackpressureStrategy.BUFFER
//        )
//                .parallel()
//                .runOn(Schedulers.computation())
//                .flatMap(conf -> {
//
//                    // 通过 Nexus2 的 REST API 异步查询。
//                    //    - 通过 RepoResDirJobConf 的回调，提交子目录的查询任务，实现递归查询
//
//                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {
//                        contentEx.getContent().getData().stream()
//                                .filter(item -> !item.isLeaf())
//                                .forEach(item -> {
//                                    logger.debug("RepoResDirJobConf 流 : emmit : onNext : " + item);
//                                    jobConfEmitterRef.get().onNext(createConf(item.getRelativePath(), null));
//                                });
//                    }));
//
//                    //    - 通过 下面额外的回调处理，使得 能确定 何时 RepoResDirJobConf 流 何时已经终止
//                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {
//                        runningJob.remove(conf);
//                        if (runningJob.isEmpty()) {
//                            logger.debug("RepoResDirJobConf 流 : emmit : onComplete");
//                            jobConfEmitterRef.get().onComplete();
//                        }
//                    }));
//                    conf.setErrorCallback(conf.getErrorCallback().andThen(err -> {
//                        runningJob.remove(conf);
//                        if (runningJob.isEmpty()) {
//                            logger.debug("RepoResDirJobConf 流 : emmit : onError : " + err);
//                            jobConfEmitterRef.get().onError(err);
//                        }
//                    }));
//
//                    //    - 通过 下面额外的 success 处理，使当前事件流能继续向后、异步地变成 RepoContentEx 流
//                    AtomicReference<FlowableEmitter<RepoContentEx>> emitterRef = new AtomicReference<>();
//                    conf.setSuccessCallback(conf.getSuccessCallback().andThen(contentEx -> {
//
//                        logger.debug("RepoContentEx 流 : emmit : onNext : " + contentEx);
//                        emitterRef.get().onNext(contentEx);
//
//                        logger.debug("RepoContentEx 流 : emmit : onComplete");
//                        emitterRef.get().onComplete();
//                    }));
//
//                    RepoResQueryJob job = new RepoResQueryJob();
//                    runningJob.add(conf);
//                    job.accept(conf);
//
//                    return Flowable.create(
//                            emitterRef::set,
//                            BackpressureStrategy.BUFFER
//                    );
//                })
//
//                // RepoContentEx 流  (都只是目录，不包含文件——叶子节点）
//                .filter(contentEx -> isSnapshotVersionDir(contentEx.getConf().getPath()))
        createRepoContentExFlow()
                .sequential()
                .subscribe(
                        contentEx -> {
                            logger.debug("RepoContentEx 流 : subscribe : onNext : " + contentEx.getConf().getPath());
                        },
                        err -> {
                            logger.debug("RepoContentEx 流 : subscribe : onError : " + err);
                            onError.accept(err);
                        },
                        () -> {
                            logger.debug("RepoContentEx 流 : subscribe : onComplete");
                            onComplete.accept(null);
                        }
                );
    }


    Flowable<RepoContentEx> repoDirs() {
        return Flowable.create(repoResSubscribe, BackpressureStrategy.BUFFER);
    }

    public void clean0() {


//        repoDirs()
//
//                // 过滤掉叶子节点
//                // 过滤掉非快照版的版本号节点
//                // ----------- 统计SNAPSHOT 下面的 排除掉 非目标项
//                // ----------- 统计SNAPSHOT 下面的 目标项 group
//                // ----------- 统计SNAPSHOT 下面的 group sort
//                // ----------- 统计SNAPSHOT 下面的 目标项 group
//                .subscribe(c -> {
//
//                    System.out.println(c.getCurRelativePath());
//                    for (RepoContent.Item item : c.getContent().getData()) {
//                        System.out.println(item.getRelativePath());
//                    }
//
//                })
//                .subscribe(c -> {
//                    if (c.getData() == null || c.getData().isEmpty()) {
//                        return;
//                    }
//
//                    c.getData().stream().forEach(item -> {
//                        if (isSnapshotVersionDir(item)) {
//                            nexus2Api.listRepoContent(repo, item.getRelativePath());
//                            //.addCallback();
//                        }
//                    });
//
//                    List<RepoContent.Item> itemList = c.getData();
//
//                })

        ;


    }
}
