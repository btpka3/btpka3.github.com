package me.test.org.apache.commons.compress.archivers.zip;

import lombok.SneakyThrows;
import me.test.jdk.java.util.zip.Zip;
import org.apache.commons.compress.archivers.examples.Expander;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author dangqian.zll
 * @date 2024/10/30
 */
public class ZipFileTest {

    @SneakyThrows
    public void x() {
        ZipFile zipFile = new ZipFile.Builder()
                .setFile(Zip.zipFile)
                .get();
        zipFile.getEntries("");
    }

    public  void unzip(File zipFile, File targetDirectory) throws IOException {
        // Ensure the target directory exists
        if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
            throw new IOException("Failed to create target directory " + targetDirectory.getAbsolutePath());
        }

        try (ZipFile archive = new ZipFile(zipFile)) {
            // The Expander class provides high-level "extract all" functionality
            new Expander().expand(archive, targetDirectory);
        }
    }

    public  void unzip(File zipFile, Path targetDirectory) throws IOException {
        // Ensure the target directory exists
        if (!Files.exists(targetDirectory)) {
            Files.createDirectories(targetDirectory);
        }

        try (ZipFile archive = new ZipFile(zipFile)) {
            // The Expander class provides high-level "extract all" functionality
            new Expander().expand(archive, targetDirectory);
        }
    }

    @SneakyThrows
    @Test
    public void createZip01() {


        File dir = new File("/tmp/createZip01");

        // 已存在，则删除并新建
        if (dir.exists()) {
            FileUtils.forceDelete(dir);
        }
        assertTrue(dir.mkdirs());
        File srcFile1 = new File(dir, "a.txt");
        FileUtils.write(srcFile1, "Hello World : " + System.currentTimeMillis(), StandardCharsets.UTF_8);
        File srcFile2 = new File(dir, "b.txt");
        FileUtils.write(srcFile2, "Nice to mtee you~ " + System.currentTimeMillis(), StandardCharsets.UTF_8);


        File zipFile = new File(dir, "demo.zip");
        try (ZipArchiveOutputStream out = new ZipArchiveOutputStream(zipFile)) {
            out.setComment("Demo comment01");
            // 演示: 顶层文件
            {
                ZipArchiveEntry entry = new ZipArchiveEntry("a.txt");
                entry.setSize(srcFile1.length());
                entry.setComment("Demo comment02");
                out.putArchiveEntry(entry);
                try (InputStream in = new FileInputStream(srcFile1)) {
                    IOUtils.copy(in, out);
                }
            }

            // 演示: 子目录里的文件
            {
                // 这里传入了 File, 会把文件的最后修改时间 写入到entry中
                ZipArchiveEntry entry = new ZipArchiveEntry(srcFile2, "bbb/b.txt");
                out.putArchiveEntry(entry);
                try (InputStream in = new FileInputStream(srcFile2)) {
                    IOUtils.copy(in, out);
                }
            }

            out.closeArchiveEntry();
        }
        unzip(zipFile, Paths.get("/tmp/createZip01_unzip"));


    }


}
