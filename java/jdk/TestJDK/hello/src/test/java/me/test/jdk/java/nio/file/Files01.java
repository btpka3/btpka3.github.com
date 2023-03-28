package me.test.jdk.java.nio.file;

import org.joda.time.Days;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;


// java.net.JarURLConnection
// https://docs.oracle.com/javase/8/docs/technotes/guides/io/fsp/zipfilesystemprovider.html
// https://stackoverflow.com/questions/25032716/getting-filesystemnotfoundexception-from-zipfilesystemprovider-when-creating-a-p
public class Files01 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        test02();
    }


    public static void test01() throws IOException, URISyntaxException {
//System.out.println(Days.class.getClassLoader().getResource("/").toURI());

        String str = "jar:file:///Users/zll/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar!/";
        URI uri = URI.create(str);

        try {
            FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException e) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystems.newFileSystem(uri, env);
        }


        Path rootPath = Paths.get(uri);


        //FileSystem fs = FileSystems.getFileSystem(jarUri);

//        Path rootPath = fs.getPath("/");
        Files.walk(rootPath)
                .forEach(path -> {
                    System.out.println(path);
                })
        ;
    }

    public static void test02() throws IOException {
        String str = "jar:file:///Users/zll/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar!/";
        URI uri = URI.create(str);

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path rootPath = zipfs.getPath("/");
            System.out.println("---");
            Files.walk(rootPath)
                    .forEach(path -> {
                        System.out.println("::" + path);
                    });
        }
    }
}
