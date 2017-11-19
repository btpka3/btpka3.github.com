package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.conf.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.regex.*;

import static java.util.stream.Collectors.*;

@Service
public class CleanDiskSnapshotByStreamService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final File[] EMPTY_FILE_ARRAY = new File[0];

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

        try {
            AtomicInteger count = new AtomicInteger(0);
            Files.walk(Paths.get(repoDir), FileVisitOption.FOLLOW_LINKS)
                    // 只保留目录
                    .filter(path -> path.toFile().isDirectory())

                    // 只保留快照版本号的目录
                    .filter(path -> isSnapshotVersionDir(path.toFile().getAbsolutePath()))
                    .forEach(path -> {
                        File verDir = path.toFile();
                        String relativePath = verDir.getAbsolutePath().replace(repoDir, "");

                        //  快照版本目录的路径 解析出相应的信息
                        ArtifactInfo artifactInfo = toSnapshotArtifactInfo(relativePath);


                        File[] files = verDir.listFiles();

                        if (files == null) {
                            files = EMPTY_FILE_ARRAY;
                        }

                        Map<String, List<File>> groupedFiles = Arrays.stream(files)
                                // 只保留 `${artifactId}-${snapshotVersion}-${yyyyMMdd.HHmmss}-N.*`
                                .filter(file -> isCandidateArtifactToDel(artifactInfo, file.getName()))
                                .collect(groupingBy(file -> getCandidatePrefixGroups(artifactInfo, file.getName()).get(1)));

                        List<Map.Entry<String, List<File>>> sortedEntries = new ArrayList<>(groupedFiles.entrySet());
                        Collections.sort(sortedEntries, (e1, e2) -> {
                            String key1 = e1.getKey();
                            String key2 = e2.getKey();

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
                        });

                        if (sortedEntries.size() <= props.getMaxSnapshotCount()) {
                            return;
                        }

                        sortedEntries.subList(0, sortedEntries.size() - props.getMaxSnapshotCount())
                                .forEach(entry -> entry.getValue().stream().forEach(f -> {
                                    Integer num = count.addAndGet(1);
                                    logger.debug("删除第 " + num + " 个文件：" + f.getAbsolutePath());
                                    f.delete();
                                }));
                    });

            logger.debug("一共删除了 " + count.get() + " 个文件");
            onComplete.run();
        } catch (IOException e) {
            onError.accept(e);
        }
    }
}
