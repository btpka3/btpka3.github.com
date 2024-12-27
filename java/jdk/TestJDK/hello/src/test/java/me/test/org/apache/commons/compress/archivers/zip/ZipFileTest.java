package me.test.org.apache.commons.compress.archivers.zip;

import lombok.SneakyThrows;
import me.test.jdk.java.util.zip.Zip;
import org.apache.commons.compress.archivers.zip.ZipFile;

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
}
