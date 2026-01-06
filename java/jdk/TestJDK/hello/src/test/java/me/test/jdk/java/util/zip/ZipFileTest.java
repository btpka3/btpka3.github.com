package me.test.jdk.java.util.zip;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/7
 */
public class ZipFileTest {

    @SneakyThrows
    public void read() {
        File f = new File("a.zip");
        ZipFile zipFile = new ZipFile(f);
        String comment = zipFile.getComment();
        zipFile.entries().asIterator()
                .forEachRemaining(zipEntry -> {
                    String entryComment = zipEntry.getComment();
                    System.out.println(zipEntry.getName());
                });
    }


    @SneakyThrows
    @Test
    public void createZip01() {

        File dir = new File("/tmp/createZip02");

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
        try (
                FileOutputStream fileOut = new FileOutputStream(zipFile);
                ZipOutputStream out = new ZipOutputStream(fileOut)
        ) {
            out.setComment("Demo comment01");
            // 演示: 顶层文件
            {
                ZipEntry entry = new ZipEntry("a.txt");
                entry.setSize(srcFile1.length());
                entry.setComment("Demo comment02");
                out.putNextEntry(entry);
                try (InputStream in = new FileInputStream(srcFile1)) {
                    IOUtils.copy(in, out);
                }
            }

            // 演示: 子目录里的文件
            {
                // 这里传入了 File, 会把文件的最后修改时间 写入到entry中
                ZipEntry entry = new ZipEntry("bbb/b.txt");

                BasicFileAttributes attrs = Files.readAttributes(srcFile2.toPath(), BasicFileAttributes.class);
                entry.setLastModifiedTime(attrs.lastModifiedTime());
                entry.setCreationTime(attrs.creationTime());
                entry.setLastAccessTime(attrs.lastAccessTime());
                out.putNextEntry(entry);
                try (InputStream in = new FileInputStream(srcFile2)) {
                    IOUtils.copy(in, out);
                }
            }
            out.closeEntry();
        }
    }
}
