package me.test.jdk.java.util.zip;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 创建zip文件。
 * <p>
 * 1G = zipped 1020K
 * 3M = zipped    4K
 * 300M = zipped  300K
 */
public class Zip {


    public static String zipFile = "/tmp/big.zip";
    public static String zipDir = "/tmp/big";


    @Test
    public void generateZipFile() throws IOException {

        new File(zipFile).delete();

        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);

        ZipEntry entry = new ZipEntry("test.txt");
        zos.putNextEntry(entry);

        WritableByteChannel channel = Channels.newChannel(zos);

        ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);

        int COUNT = 12;
        // COUNT =  300* 1024 * 1024;

        int c = 0;
        while (c < COUNT) {
            int fillCount = COUNT - c >= buf.capacity() ? buf.capacity() : COUNT - c;

            fillBuf(buf, fillCount);

            buf.flip();
            channel.write(buf);
            buf.compact();
            c += fillCount;
        }

        channel.close();


        System.out.printf("Done. see : " + zipFile);

    }

    static void fillBuf(ByteBuffer buf, int count) {

        assert count <= buf.remaining() : "count must less than remaining: " + count + ", " + buf.remaining();
        for (int i = 0; i < count; i++) {
            buf.put((byte) 'A');
        }

    }

    @SneakyThrows
    @Test
    public void unzipFile() {
        FileUtils.deleteDirectory(new File(zipDir));
        Path zipFilePath = Path.of(zipFile);
        Path parentDir = zipFilePath.getParent();
        String fileName = zipFilePath.toFile().getName();
        Path targetDir = parentDir.resolve(FilenameUtils.removeExtension(fileName));

        //Open the file
        try (ZipFile zip = new ZipFile(zipFilePath.toFile())) {

            FileSystem fileSystem = FileSystems.getDefault();
            Enumeration<? extends ZipEntry> entries = zip.entries();

            //We will unzip files in this folder
            if (!targetDir.toFile().isDirectory()
                    && !targetDir.toFile().mkdirs()) {
                throw new IOException("failed to create directory " + targetDir);
            }

            //Iterate over entries
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                File f = new File(targetDir.resolve(Path.of(entry.getName())).toString());

                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                }

                //Else create the file
                else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }

                    try (InputStream in = zip.getInputStream(entry)) {
                        Files.copy(in, f.toPath());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    @Test
    public void unzipSubDir() {
        File dir = new File(zipDir);
        FileUtils.deleteDirectory(dir);
        dir.mkdirs();
        Path targetDir = dir.toPath();

        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("=====url=" + url);
        ZipFile zipFile = new ZipFile(url.getFile());
        zipFile.stream()
                .filter(entry -> entry.getName().startsWith("META-INF"))
                .filter(entry -> !entry.isDirectory())
                .forEach(entry -> {
                    File f = new File(targetDir.resolve(Path.of(entry.getName())).toString());
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }
                    try (InputStream in = zipFile.getInputStream(entry)) {
                        Files.copy(in, f.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("===:" + entry.getName());
                });
    }
}
