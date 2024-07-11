package me.test.biz;

import org.apache.commons.io.file.PathFilter;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2024/5/16
 */
public class CleanPC {

    boolean doDelete = true;

    int maxDepth = 50;
    FileTime lastAccessTime = FileTime.fromMillis(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 31 * 6);

    @Test
    public void clearMavenLocalRepo() throws IOException {
        /*
        # macos 版本的 stat
        # 显示 birthtime, ctime, mtime, atime
        stat -f "%SB   %Sc   %Sm   %Sa  %N" -t "%Y-%m-%d %H:%M:%S" *
        */
        String home = System.getProperty("user.home");
        Path mavenLocalRepo = Path.of(home, ".m2", "repository");
        BasicFileAttributes attrs = Files.readAttributes(mavenLocalRepo, BasicFileAttributes.class);
        FileTime time = attrs.lastAccessTime();
        System.out.println(time);

        Set<Path> versionDirs = findVersionDir(mavenLocalRepo);
        AtomicLong deleteFileSize = new AtomicLong(0);
        versionDirs.stream()
                .filter(this::shouldDeleteVersionDir)
                .peek(path -> deleteFileSize.addAndGet(getAllFileSizeInDir(path)))
                .forEach(path -> System.out.println("CAN_BE_DELETED : " + path));
        ;
        System.out.println("deleteFileSize : " + (1.0 * deleteFileSize.get() / 1024 / 1024 / 1024) + "G");


        if (doDelete) {
            versionDirs.stream().filter(this::shouldDeleteVersionDir)
                    .forEach(path -> {
                        try {
                            PathUtils.deleteDirectory(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("deleted");
        }
    }

    protected long getAllFileSizeInDir(Path dir) {
        PathFilter pathFilter = FileFilterUtils.fileFileFilter();
        try {
            return PathUtils.walk(dir, pathFilter, maxDepth, false)
                    .mapToLong(path -> {
                        try {
                            return Files.size(path);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();
        } catch (IOException e) {
            return 0L;
        }
    }

    protected boolean shouldDeleteVersionDir(Path versionDir) {
        PathFilter pathFilter = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.or(
                        FileFilterUtils.suffixFileFilter(".jar"),
                        FileFilterUtils.suffixFileFilter(".pom")
                )
        );
        try {
            return PathUtils.walk(versionDir, pathFilter, 1, false)
                    // 全部 最后访问时间都在在给定的时间之前，就可以删除
                    .allMatch(path -> {
                        try {
                            BasicFileAttributes att = Files.readAttributes(path, BasicFileAttributes.class);
                            return att.lastAccessTime().compareTo(lastAccessTime) < 0;
                        } catch (Throwable t) {
                            return true;
                        }
                    });
        } catch (IOException e) {
            return false;
        }
    }

    protected Set<Path> findVersionDir(Path mavenLocalRepo) throws IOException {
        PathFilter pathFilter = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".jar")
        );
        return PathUtils.walk(mavenLocalRepo, pathFilter, 30, false)
                .map(Path::getParent)
                .collect(Collectors.toSet());
    }

    /**
     * @see <a href="https://docs.gradle.org/current/userguide/directory_layout.html">Gradle User Home directory</a>
     */
    protected void cleanGradle() {
        // NOOP
    }

    @Test
    protected void cleanLogs() {
        String userHome = System.getProperty("user.home");
        List<Path> dirsToBeDelete = Arrays.asList(
                Path.of(userHome, "mteebundle"),
                Path.of(userHome, "vipsrv-logs"),
                Path.of(userHome, "logs"),
                Path.of(userHome, "mtee3/logs"),
                Path.of(userHome, "csis/logs"),
                Path.of(userHome, "app/logs"),
                Path.of(userHome, "diamond/logs"),
                Path.of(userHome, "green-console/logs"),
                Path.of(userHome, "gong9-mw-demo-boot/logs")
        );

        dirsToBeDelete.forEach(path -> {
            try {
                System.out.println("DELETE: " + path);
                PathUtils.deleteDirectory(path);
            } catch (NoSuchFileException e) {
                // NOOP
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
