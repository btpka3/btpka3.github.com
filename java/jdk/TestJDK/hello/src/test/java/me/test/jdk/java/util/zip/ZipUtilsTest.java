package me.test.jdk.java.util.zip;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.PathUtils;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class ZipUtilsTest {

    /**
     * 压缩目录
     */
    @SneakyThrows
    @Test
    public void testCreateZipFile1() {
        Path base = Path.of("/tmp/ZipUtilsTest01");
        Path dir = base.resolve("src");
        // 准备目录
        {
            // 已存在，则删除并新建
            if (Files.exists(dir)) {
                PathUtils.delete(dir);
            }
            Files.createDirectories(dir);

            IOUtils.copy(
                    new StringReader("Hello World : " + System.currentTimeMillis()),
                    Files.newOutputStream(dir.resolve("a.txt")),
                    StandardCharsets.UTF_8
            );
            Path subDir = dir.resolve("bbb");
            Files.createDirectories(subDir);
            IOUtils.copy(
                    new StringReader("Nice to mtee you~ " + System.currentTimeMillis()),
                    Files.newOutputStream(subDir.resolve("b.txt")),
                    StandardCharsets.UTF_8
            );
        }

        // 创建zip文件
        ZipUtils.createZipFile(dir, base.resolve("demo.zip"));

    }

    /**
     * 压缩单个文件
     */
    @SneakyThrows
    @Test
    public void testCreateZipFile2() {
        Path base = Path.of("/tmp/ZipUtilsTest01");
        Path src = base.resolve("a.txt");
        // 准备目录
        {
            // 已存在，则删除并新建
            if (Files.exists(base)) {
                PathUtils.delete(base);
            }
            Files.createDirectories(base);

            IOUtils.copy(
                    new StringReader("Hello World : " + System.currentTimeMillis()),
                    Files.newOutputStream(src),
                    StandardCharsets.UTF_8
            );
        }

        // 创建zip文件
        ZipUtils.createZipFile(src, base.resolve("demo.zip"));

    }
}
