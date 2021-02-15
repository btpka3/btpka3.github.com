package me.test.reactor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CleanMavenLocalRepo {

    static final Logger log = LoggerFactory.getLogger(CleanMavenLocalRepo.class);

    @Test
    public void test() throws IOException {

        String m2LocalRepoRoot = System.getProperty("user.home") + "/.m2/repository";


        Flux.fromStream(Files.walk(Paths.get(m2LocalRepoRoot)))
                .map(Path::toFile)
                .filter(this::isArtifactDir)
                .subscribe(
                        this::clean,
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                )
        ;

        log.info("oo0000");
    }

    public boolean isArtifactDir(File file) {
        if (!file.isDirectory()) {
            return false;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return false;
        }

        return Stream.of(files)
                .anyMatch(this::isVersionDir);
    }

    public boolean isVersionDir(File file) {
        if (!file.isDirectory()) {
            return false;
        }
        String[] files = file.list();
        if (files == null) {
            return false;
        }
        for (String child : files) {

            if ("maven-metadata-local.xml".equals(child)) {
                return true;
            }
            if ("_remote.repositories".equals(child)) {
                return true;
            }
            if (child.matches("^.*\\.pom$")) {
                return true;
            }
            if (child.matches("^.*\\.jar$")) {
                return true;
            }
        }
        return false;
    }

    public void clean(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }


        List<Pair<File, DefaultArtifactVersion>> list = Stream.of(files)
                .filter(File::isDirectory)
                .map(file -> Pair.of(file, new DefaultArtifactVersion(file.getName())))

                .sorted(Comparator.comparing(Pair<File, DefaultArtifactVersion>::getValue))
                .collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            Pair<File, DefaultArtifactVersion> pair = list.get(i);
            File file = pair.getKey();
            DefaultArtifactVersion ver = pair.getValue();

            if (ver.getMajorVersion() == 0 && ver.getMinorVersion() == 0 && ver.getIncrementalVersion() == 0) {
                log.warn("invalid version    : " + file.getAbsolutePath());
                continue;
            }

            if (i < list.size() - 3) {
                log.info("delete version dir : " + file.getAbsolutePath());
//                try {
//                    FileUtils.deleteDirectory(file);
//                } catch (IOException e) {
//                    log.error(e.getMessage(), e);
//                }
            } else {
                //log.info("keep version dir   : " + file.getAbsolutePath());
            }

        }
    }


    @Test
    public void test01() {
        // `${Major}.${minor}.${inc}-${builderNumber}`
        // `${Major}.${minor}.${inc}-${qualifier}`

        //ArtifactVersion ver = new DefaultArtifactVersion("1.2.3-SNAPSHOP-5");
        ArtifactVersion ver = new DefaultArtifactVersion("0.9.1.1");
        System.out.println(ver);
        System.out.println(ver.getMajorVersion());
        System.out.println(ver.getMinorVersion());
        System.out.println(ver.getIncrementalVersion());
        System.out.println(ver.getBuildNumber());
        System.out.println(ver.getQualifier());
    }

}
