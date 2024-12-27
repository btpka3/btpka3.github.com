package me.test.com.esotericsoftware.kryo;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author dangqian.zll
 * @date 2024/9/29
 */
public class AaaTest {

    @Test
    public void x() throws IOException {
        String fileName ="a4ac4990-456d-4b03-850a-c5bc1db50a9c.json";
        File filePath = Path.of(System.getProperty("user.home"), "Downloads", fileName).toFile();
        byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));

    }
}
