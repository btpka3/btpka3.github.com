package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import io.reactivex.*;
import io.reactivex.parallel.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.regex.*;

@Service
public class CleanSnapshotService {

    @Autowired
    Nexus2Api nexus2Api;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean isSnapshotVersionDir(String path) {
        return path.endsWith("-SNAPSHOT/");
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
        info.setPath(relativePath);
        return info;
    }

    private Pattern getCandidatePattern(ArtifactInfo info) {
        String patternStr = "(("
                + info.getArtifactId()
                + "-"
                + info.getVersionNumber().replaceAll("\\.", "\\\\.")
                + "-\\d+{8}\\.\\d{6})-"
                + "(\\d+)"
                + ")"
                + ".*";


        return Pattern.compile(patternStr);
    }

    private boolean isCandidateArtifactToDel(ArtifactInfo info, String artifactFileName) {
        Pattern p = getCandidatePattern(info);
        Matcher m = p.matcher(artifactFileName);
        return m.matches();
    }

    private List<String> getCandidatePrefixGroups(ArtifactInfo info, String artifactFileName) {
        Pattern p = getCandidatePattern(info);
        Matcher m = p.matcher(artifactFileName);
        Assert.isTrue(m.matches(), "不是候选删除对象 ： " + info.getPath() + artifactFileName);
        return Arrays.asList(
                m.group(0),  // full name
                m.group(1),  // 到时间戳后面的序号（含序号）
                m.group(2),  // 到时间戳（不含时间戳后面的序号）
                m.group(3)  // 只有序号
        );
    }


    private RepoResDirJobConf createConf(String repo, String path) {
        RepoResDirJobConf conf = new RepoResDirJobConf();
        conf.setRepo(repo);
        conf.setPath(path);
        return conf;
    }


    private void queryRepoRsc(
            RepoResDirJobConf conf,
            Consumer<RepoContentEx> successCallback,
            Consumer<Throwable> errorCallback
    ) {
        String repo = conf.getRepo();
        String path = conf.getPath();

        logger.debug("==================== 开始查询 : {}", path);
        nexus2Api.listRepoContent(repo, path)
                .addCallback(
                        c -> {
                            RepoContentEx p = new RepoContentEx();
                            p.setConf(conf);
                            p.setContent(c.getBody());
                            successCallback.accept(p);
                        },
                        errorCallback::accept
                );
    }


    private Set<RepoResDirJobConf> runningJob = new LinkedHashSet<>();


    private ParallelFlowable<RepoContentEx> createRepoContentExFlow(RepoResDirJobConf jobConf) {
        AtomicReference<FlowableEmitter<RepoResDirJobConf>> jobConfEmitterRef = new AtomicReference<>();

        // RepoResDirJobConf 流
        return Flowable.<RepoResDirJobConf>create(

                e -> {
                    jobConfEmitterRef.set(e);
                    e.onNext(jobConf);
                },
                BackpressureStrategy.BUFFER
        )
                .parallel()
                //.runOn(Schedulers.computation())
                .flatMap(conf -> {

                    // 通过 Nexus2 的 REST API 异步查询。
                    //    - 通过 RepoResDirJobConf 的回调，提交子目录的查询任务，实现递归查询

                    Consumer<RepoContentEx> successCallback = (contentEx ->
                            contentEx.getContent().getData().stream()
                                    .filter(item -> !item.isLeaf())
                                    .forEach(item -> {
                                        logger.trace("RepoResDirJobConf 流 : emmit : onNext : " + item);
                                        jobConfEmitterRef.get().onNext(createConf(
                                                contentEx.getConf().getRepo(),
                                                item.getRelativePath()));
                                    })
                    );

                    //    - 通过 下面额外的回调处理，使得 能确定 何时 RepoResDirJobConf 流 何时已经终止
                    successCallback = successCallback.andThen(contentEx -> {
                        runningJob.remove(conf);
                        if (runningJob.isEmpty()) {
                            logger.trace("RepoResDirJobConf 流 : emmit : onComplete");
                            jobConfEmitterRef.get().onComplete();
                        }
                    });
                    Consumer<Throwable> errorCallback = (err -> {
                        runningJob.remove(conf);
                        if (runningJob.isEmpty()) {
                            logger.debug("RepoResDirJobConf 流 : emmit : onError : " + err);
                            jobConfEmitterRef.get().onError(err);
                        }
                    });

                    //    - 通过 下面额外的 success 处理，使当前事件流能继续向后、异步地变成 RepoContentEx 流
                    AtomicReference<FlowableEmitter<RepoContentEx>> emitterRef = new AtomicReference<>();
                    successCallback = successCallback.andThen(contentEx -> {

                        logger.debug("RepoContentEx 流 : emmit : onNext : " + contentEx);
                        emitterRef.get().onNext(contentEx);

                        logger.debug("RepoContentEx 流 : emmit : onComplete");
                        emitterRef.get().onComplete();
                    });

                    runningJob.add(conf);
                    queryRepoRsc(conf, successCallback, errorCallback);

                    return Flowable.create(
                            emitterRef::set,
                            BackpressureStrategy.BUFFER
                    );
                });
    }

    private Flowable<Map<String, List<RepoContent.Item>>> getToBeDelItems(RepoContentEx contentEx) {

        //  快照版本目录的路径 解析出相应的信息
        ArtifactInfo artifactInfo = toSnapshotArtifactInfo(contentEx.getConf().getPath());

        return Flowable.fromIterable(contentEx.getContent().getData())

                // 只保留 `${artifactId}-${snapshotVersion}-${yyyyMMdd.HHmmss}-N.*`
                .filter(item -> isCandidateArtifactToDel(artifactInfo, item.getText()))

                // 分组
                .groupBy(item -> getCandidatePrefixGroups(artifactInfo, item.getText()).get(1))

                .parallel()
                .flatMap(gf ->
                        // 分组后，必须先消费完内部分组flow的消息。
                        // 将 GroupedFlowable<String,Item> -> Map<String, List<Item>>
                        gf
                                .collect(
                                        () ->
                                                Collections.<String, List<RepoContent.Item>>singletonMap(
                                                        gf.getKey(),
                                                        new ArrayList<>()
                                                )
                                        ,
                                        (m, i) -> m.get(gf.getKey()).add(i)
                                )
                                .toFlowable()
                )

                // 排序
                .sorted((m1, m2) -> {

                    String key1 = new ArrayList<>(m1.keySet()).get(0);
                    String key2 = new ArrayList<>(m2.keySet()).get(0);

                    List<String> g1 = getCandidatePrefixGroups(artifactInfo, key1);
                    List<String> g2 = getCandidatePrefixGroups(artifactInfo, key2);

                    // 比较时间戳部分
                    int c = Comparator.<String>naturalOrder().compare(g1.get(2), g2.get(2));
                    if (c != 0) {
                        return c;
                    }

                    // 比较序号部分
                    int i1 = Integer.parseInt(g1.get(3));
                    int i2 = Integer.parseInt(g2.get(3));
                    return Comparator.<Integer>naturalOrder().compare(i1, i2);
                })

                // 保留 最新(最后) 的 N 个不删除
                .skipLast(contentEx.getConf().getMaxSnapshotCount());
    }

    /**
     * 按组删除要删除的内容。并全部删除完毕后返回。
     *
     * 返回的 flowable 只会 发送 onComplete 或 onError，而不会发送 onNext。
     */
    private Flowable<String> delRepoRscByGroup(String key, List<RepoContent.Item> items) {

        return Flowable.fromIterable(items)

                // 并发进行删除
                .parallel()

                // 执行删除
                .flatMap(item -> Flowable.<RepoContent.Item>create(

                        // FIXME: 该 API 原本是想设计为 delRepoContent(String repoId, String relativePath) 的
                        e -> nexus2Api.delRepoContent(item.getResourceURI())
                                .addCallback(
                                        result -> {
                                            e.onNext(item);
                                            e.onComplete();
                                        },
                                        e::onError

                                ),
                        BackpressureStrategy.BUFFER)
                )
                .sequential()

                // 全部删除完成后，合并为一个 完成消息
                .collect(() -> key, (k, i) -> {
                    // do nothing
                })
                .toFlowable();
    }


    public void clean(
            RepoResDirJobConf conf,
            Runnable onComplete,
            Consumer<Throwable> onError
    ) {

        // createConf("snapshots", "/net/kingsilk/qh-activity-api/")
        createRepoContentExFlow(conf)

                // RepoContentEx 流  (都只是目录，不包含文件——叶子节点）
                // 只保留 "-SNAMPSHOTP/" 的 快照版本号 的目录
                .filter(contentEx -> isSnapshotVersionDir(contentEx.getConf().getPath()))

                //  分组，排序，得到要删除的记录集。
                .flatMap(this::getToBeDelItems)

                .flatMap(m -> {
                    //  按组删除记录集
                    Map.Entry<String, List<RepoContent.Item>> entry = new ArrayList<>(m.entrySet()).get(0);
                    return delRepoRscByGroup(entry.getKey(), entry.getValue());
                })

                .sequential()

                .subscribe(
                        o -> logger.debug("subscribe : onNext : 已经删除 " + o),
                        err -> {
                            logger.error("subscribe : onError : ", err);
                            onError.accept(err);
                        },
                        () -> {
                            logger.debug("subscribe : onComplete");
                            onComplete.run();
                        }
                );
    }

}
