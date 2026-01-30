package me.test.jdk.java.io;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import org.junit.jupiter.api.Test;

public class File {


    public static void main(String[] args) throws IOException {

        //listFiles("/tmp");
        //walkFileTree("/tmp");
        //walk("/tmp");
        fileIteratable("/data0");
    }


    public static void listFiles(String dir) {
        System.out.println("----------------------------------- listFiles");
        BlockingDeque<java.io.File> q = new LinkedBlockingDeque<>();
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


    @Test
    public void walkFileTree() throws IOException {
        System.out.println("----------------------------------- walkFileTree");

        String dir = "/tmp";
        //String dir = "/tmp/createZip02/b.txt";
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

    public static void walk(String dir) throws IOException {
        System.out.println("----------------------------------- walk");

        Files.walk(Paths.get(dir), FileVisitOption.FOLLOW_LINKS)
                //.peek(System.out::println)
                .forEach(System.out::println);

        System.out.println("done.");
    }


    public static void fileIteratable(String dir) throws IOException {


        System.out.println("----------------------------------- fileIteratable : " + dir);

        System.out.println(Paths.get("/aa/bb", "cc", "dd"));


        String p = "/aa/bb/cc";
        Paths.get(p).forEach(a ->
                // 分别打印 "aa", "bb", "cc"
                System.out.println("@@@@@@@@@ : " + a)
        );
        for (Path path : Paths.get(p)) {
            System.out.println("========= : " + path);
        }
        System.out.println("done.");
    }
}
