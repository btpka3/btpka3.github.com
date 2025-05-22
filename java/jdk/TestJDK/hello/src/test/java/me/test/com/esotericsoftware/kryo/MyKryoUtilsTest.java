package me.test.com.esotericsoftware.kryo;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/4/24
 */
public class MyKryoUtilsTest {

    @Test
    public void serialize01() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("a", "aaa");
    }

    @Test
    public void test() {
        byte b = (byte) (MyKryoUtils.VERSION | 0);
        System.out.println(b);
        Assertions.assertEquals(b, MyKryoUtils.VERSION);
    }

    @SneakyThrows
    @Test
    public void prepareWarmUp() {

        String event = "event";
        Path srcPath = Path.of("/tmp/preHot/" + event + ".jsonl");
        Path destPath = Path.of("/tmp/preHot/" + event);
        File srcFile = srcPath.toFile();
        File destFile = destPath.toFile();
        Assertions.assertTrue(srcFile.exists());
        Assertions.assertTrue(srcFile.isFile());
        if (destFile.exists()) {
            Assertions.assertTrue(destFile.delete());
        }
        List<String> allLines = Files.readAllLines(srcPath);
        allLines.stream()
                .filter(StringUtils::isNotBlank)
                .map(JSON::parseObject)
                .map(map -> MyKryoUtils.serialize("kryo", map))
                .map(bytes -> Base64.getEncoder().encode(bytes))
                .forEach(base64Bytes -> {
                    try {
                        FileUtils.writeByteArrayToFile(destFile, base64Bytes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println("Done.");
    }


}
