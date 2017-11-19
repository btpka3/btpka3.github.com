package io.github.btpka3.nexus.clean.snapshot;

import org.junit.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class FileWalkTest {


    @Test
    public void walkFileTree() throws IOException {
        System.out.println("----------------------------------- walkFileTree");

        String dir = "/tmp";
        Files.walkFileTree(
                Paths.get(dir),
                EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        System.out.println(dir.toFile().getAbsolutePath());
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        System.out.println(file.toFile().getAbsolutePath());
                        return FileVisitResult.CONTINUE;
                    }
                });

        System.out.println("done.");
    }


}
