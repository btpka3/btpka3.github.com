package me.test.org.apache.commons.compress.archivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import me.test.jdk.java.util.zip.Zip;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/10/30
 */
public class ArchiveStreamFactoryTest {

    @SneakyThrows
    public void x() {
        String jar = "";
        InputStream inputStream = new FileInputStream(new File(jar));
        ArchiveInputStream input = new ArchiveStreamFactory()
                .createArchiveInputStream(inputStream);
    }

    @Test
    public void extractZip() throws IOException {
        Path zipFilePath = Path.of(Zip.zipFile);
        FileUtils.deleteDirectory(new File(Zip.zipDir));

        Path parentDir = zipFilePath.getParent();
        String fileName = zipFilePath.toFile().getName();
        Path targetDir = parentDir.resolve(FilenameUtils.removeExtension(fileName));

        ArchiveStreamFactory archiveStreamFactory = new ArchiveStreamFactory();

        try (InputStream inputStream = Files.newInputStream(zipFilePath);
             ArchiveInputStream archiveInputStream = archiveStreamFactory
                     .createArchiveInputStream(ArchiveStreamFactory.ZIP, inputStream)) {

            ArchiveEntry archiveEntry = null;
            while ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                Path path = Paths.get(targetDir.toString(), archiveEntry.getName());
                File file = path.toFile();
                if (archiveEntry.isDirectory()) {
                    if (!file.isDirectory()) {
                        file.mkdirs();
                    }
                } else {
                    File parent = file.getParentFile();
                    if (!parent.isDirectory()) {
                        parent.mkdirs();
                    }
                    try (OutputStream outputStream = Files.newOutputStream(path)) {
                        IOUtils.copy(archiveInputStream, outputStream);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArchiveException e) {
            throw new RuntimeException(e);
        }
    }
}
