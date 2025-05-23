package me.test.org.apache.commons.codec;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * @author dangqian.zll
 * @date 2024/7/2
 */
public class GzipBase64UtilsTest {

    @Test
    public void testDecode() throws IOException {
        String gzipBase64Str = IOUtils.toString(new FileInputStream("/tmp/data.txt"), StandardCharsets.UTF_8);
        byte[] bytes = GzipBase64Utils.getBytesFromGzipBase64(gzipBase64Str);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    public void getGzipBase64FromBytes201() {
        String str = "hello中国";
        String base64Str = GzipBase64Utils.getGzipBase64FromBytes2(str.getBytes(StandardCharsets.UTF_8));
        byte[] resultBytes = GzipBase64Utils.getBytesFromGzipBase642(base64Str);
        String result = new String(resultBytes, StandardCharsets.UTF_8);
        Assertions.assertEquals(str, result);
        Paths.get("/tmp/data.txt");
    }

}
