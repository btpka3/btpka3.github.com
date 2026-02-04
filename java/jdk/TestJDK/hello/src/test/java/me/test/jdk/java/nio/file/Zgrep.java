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
 * @date 2018-11-05
 */
public class Zgrep {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("usage : cmd searchKey searchPath");
            System.exit(-1);
        }

        String searchKey = args[0];
        String searchPath = args[1];

        Files.walk(Paths.get(searchPath))
                .filter(path -> path.toString().endsWith(".jar"))
                .flatMap(path -> findInzip(path, searchKey))
                .forEach(System.out::println);

        System.out.println("Done.");
    }

    public static Stream<URI> findInzip(Path zipFilePath, String searchKey) {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try {

            // 注意：因为 stream 调用的原因，zipFs 不能使用 try(zipfs){...} 的形式
            // 而是需要 在 Stream#onClose 中 close 掉。
            FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + zipFilePath.toUri()), env);
            Path rootPath = zipfs.getPath("/");
            return Files.walk(rootPath)
                    .filter(path -> path.toString().endsWith(".class"))
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
                            if (content.contains(searchKey)) {
                                return Stream.of(path.toUri());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Stream.empty();
                    });
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }
}
