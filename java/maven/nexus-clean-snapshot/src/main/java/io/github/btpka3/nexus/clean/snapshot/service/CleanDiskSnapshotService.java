package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.conf.*;
import io.reactivex.*;
import org.reactivestreams.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.regex.*;

@Service
public class CleanDiskSnapshotService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean isSnapshotVersionDir(String path) {
        return path.endsWith("-SNAPSHOT");
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


    private void dd(String rootDir) throws IOException {
        Files.walk(Paths.get(rootDir), FileVisitOption.FOLLOW_LINKS)
                // 只保留目录
                .filter(path -> path.toFile().isDirectory())

                // 只保留快照版本号的目录
                .filter(path -> isSnapshotVersionDir(path.toFile().getAbsolutePath()));

    }

    /**
     * 返回
     */
    private Flowable<Path> createDirFlow(String rootDir) {

        Iterator<Path> pathIterator;
        try {
            pathIterator = Files.walk(Paths.get(rootDir), FileVisitOption.FOLLOW_LINKS).iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 必须先缓存，否则，后面删除了之后，这里的再便利会抛出 找不到文件的异常。


        List<Path> dirs = Flowable.<Path, Iterator<Path>>generate(() -> pathIterator, (iterator, emitter) -> {
            if (iterator.hasNext()) {
                emitter.onNext(iterator.next());
            } else {
                logger.trace("createDirFlow : generate : emit onComplete");
                emitter.onComplete();
            }
        })
                //.subscribeOn(Schedulers.single())

                // 只保留目录
                .filter(path -> path.toFile().isDirectory())

                // 只保留快照版本号的目录
                .filter(path -> isSnapshotVersionDir(path.toFile().getAbsolutePath()))

                // 提供 debug 信息
                .doOnNext(path -> logger.trace("createDirFlow : doOnNext : " + path))
                .doOnError(err -> logger.trace("createDirFlow : doOnError: ", err))
                .doOnComplete(() -> logger.trace("createDirFlow : doOnComplete "))
                .toList() // 必须先结束，否则后面因已经删除了，再遍历而出错。
                .blockingGet();

        return Flowable.fromIterable(dirs);
    }


    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    private Flowable<Map<String, List<File>>> getToBeDelItems(
            Props props,
            Path verDirPath
    ) {

        File verDir = verDirPath.toFile();
        String repoDir = getRepoDir(props);
        String relativePath = verDir.getAbsolutePath().replace(repoDir, "");

        //  快照版本目录的路径 解析出相应的信息
        ArtifactInfo artifactInfo = toSnapshotArtifactInfo(relativePath);

        File[] files = verDir.listFiles();

        if (files == null) {
            files = EMPTY_FILE_ARRAY;
        }

        return Flowable.fromArray(files)

                // 只保留 `${artifactId}-${snapshotVersion}-${yyyyMMdd.HHmmss}-N.*`
                .filter(file -> isCandidateArtifactToDel(artifactInfo, file.getName()))

                // 分组
                .groupBy(file -> getCandidatePrefixGroups(artifactInfo, file.getName()).get(1))

                //.parallel() // 分组操作单线程应该性能就够了
                .flatMap(gf ->
                        // 分组后，必须先消费完内部分组flow的消息。
                        // 将 GroupedFlowable<String,File> -> Map<String, List<File>>
                        gf
                                .collect(
                                        () ->
                                                Collections.<String, List<File>>singletonMap(
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
                .skipLast(props.getMaxSnapshotCount())
                //.doOnNext(m -> logger.trace("getToBeDelItems : doOnNext: " + verDirPath))
                .doOnError(err -> logger.trace("getToBeDelItems : doOnError: " + verDirPath, err))
                .doOnComplete(() -> logger.trace("getToBeDelItems : doOnComplete : " + verDirPath))
                ;
    }

    /**
     * 按组删除要删除的内容。并全部删除完毕后返回。
     *
     * 返回的 flowable 只会 发送 onComplete 或 onError，而不会发送 onNext。
     */
    private Flowable<String> delRepoRscByGroup(String key, List<File> files) {

        return Flowable.fromIterable(files)
                .flatMap(f -> {
                    //logger.debug("delRepoRscByGroup : fromIterable ：" + f.getAbsolutePath());
                    return Flowable.just(f.delete());
                })

                // FIXME: 阻塞
//                .observeOn(Schedulers.io())
//                .reduce((x, y) -> {
//
//                    logger.debug("delRepoRscByGroup : reduce ：x = " + x + ", y = " + y);
//                    return x && y;
//                })
//                .toFlowable()
                .map(result -> {

                    //logger.debug("delRepoRscByGroup : map ：result = " + result);
                    return key;
                })
//                // 全部删除完成后，合并为一个 完成消息
//                .collect(() -> key, (k, deleteResult) -> {
//                    // do nothing
//                })
//                .toFlowable()
                //.doOnNext(r -> logger.trace("delRepoRscByGroup ： doOnNext : " + r))
                .doOnError(err -> logger.trace("delRepoRscByGroup : doOnError : " + key, err))
                .doOnComplete(() -> logger.trace("delRepoRscByGroup : doOnComplete : " + key))
                ;
    }


    private String getRepoDir(Props props) {
        return Paths.get(
                props.getSonartypeWorkDir(), "nexus", "storage",
                props.getRepoId()
        )
                .toFile()
                .getAbsolutePath();
    }

    public void clean(
            Props props,
            Runnable onComplete,
            Consumer<Throwable> onError
    ) {

        String repoDir = getRepoDir(props);

        // createConf("snapshots", "/net/kingsilk/qh-activity-api/")

        AtomicInteger counter = new AtomicInteger(0);


        AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();
        createDirFlow(repoDir)

                // 分组，排序
                .flatMap(path -> getToBeDelItems(props, path))


//                .subscribe(
//                        key -> {
//                            logger.debug("clean() : subscribe : onNext : 删除了 " +key);
//
//                        },
//                        err -> {
//                            logger.debug("clean() : subscribe : onError : 出错了", err);
//                            onError.accept(err);
//                        },
//                        () -> {
//                            logger.debug("clean() : subscribe : onComplete : 结束了");
//                            onComplete.run();
//                        }
////                        ,
////                        subscription -> {
////                            subscriptionRef.set(subscription);
////                            logger.debug("开始订阅");
////                            //subscription.request(1);
////                        }
//
//                );

                .doOnError(err -> logger.trace("分组，排序 : doOnError : ", err))
                .doOnComplete(() -> logger.trace("分组，排序 : doOnComplete "))
                //.observeOn(Schedulers.computation())

                // 删除
                .flatMap(m -> {
                    //  按组删除记录集
                    Map.Entry<String, List<File>> entry = new ArrayList<>(m.entrySet()).get(0);
                    return delRepoRscByGroup(entry.getKey(), entry.getValue());
                })


//                .doOnNext(m -> {
//                    Map.Entry<String, List<File>> entry = new ArrayList<>(m.entrySet()).get(0);
//                    entry.getValue().stream().forEach(File::delete);
//                    logger.trace("删除 ： doOnNext : " + m);
//                })
//                .doOnError(err -> logger.trace("删除 : doOnError : ", err))
//                .doOnComplete(() -> logger.trace("删除 : doOnComplete "))

                //.onTerminateDetach()
//                .doOnSubscribe(s -> {
//                    subscriptionRef.set(s);
//                    logger.debug("clean ： doOnSubscribe : " + s);
//                })

                .subscribe(
                        key -> {
                            logger.debug("clean() : subscribe1 : onNext : 删除了 " + key + ", " + counter.addAndGet(1));
                            subscriptionRef.get().request(1);
                        },
                        err -> {
                            logger.debug("clean() : subscribe : onError : 出错了", err);
                            onError.accept(err);
                        },
                        () -> {
                            logger.debug("clean() : subscribe : onComplete : 结束了 : " + counter.get());
                            onComplete.run();
                            subscriptionRef.get().cancel();
                        }

                        // FIXME: 为何这里订阅了，就阻塞掉了？
                        ,
                        subscription -> {
                            subscriptionRef.set(subscription);
                            logger.debug("开始订阅");
                            subscription.request(1);
                        }

                );
    }
}
