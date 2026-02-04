package me.test.jdk.java.nio.file;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;

/**
 * 在 jar 中找特定内容
 *
 * @date 2018-12-03
 */
public class Zfind {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage : cmd searchPath");
            System.exit(-1);
        }

        String searchPath = args[0];

        Files.walk(Paths.get(searchPath))
                .filter(path -> path.toString().endsWith(".jar"))
                .filter(path -> !path.toFile().isDirectory())
                .flatMap(path -> findInzip(path))
                .forEach(System.out::println);

        System.out.println("Done.");
    }

    public static boolean pathMatchInZip(Path path) {
        //        return true;
        return path.toString().endsWith(".class");
        //        return path.toString().contains("com.alibaba.engine.core.operator.Operator");
    }

    public static boolean contentMatchInZip(String content) {

        //        return content.contains("jmenv.tbsite.net");
        return content.contains("WEB-INF/logback.xml");
        // return true;
    }

    public static Stream<URI> findInzip(Path zipFilePath) {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try {

            // 注意：因为 stream 调用的原因，zipFs 不能使用 try(zipfs){...} 的形式
            // 而是需要 在 Stream#onClose 中 close 掉。
            FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + zipFilePath.toUri()), env);
            Path rootPath = zipfs.getPath("/");
            return Files.walk(rootPath)
                    .filter(Zfind::pathMatchInZip)
                    .onClose(() -> {
                        try {
                            zipfs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .flatMap(path -> {
                        try {
                            String content = IOUtils.toString(Files.newInputStream(path));
                            if (contentMatchInZip(content)) {
                                return Stream.of(path.toUri());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Stream.empty();
                    });
        } catch (Exception e) {
            System.out.println("===============++########" + zipFilePath);
            e.printStackTrace();
            System.err.println("ERROR PATH : " + zipFilePath);
            return Stream.empty();
        }
    }
}
