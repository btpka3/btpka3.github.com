package me.test.jdk.jfr;


import jdk.jfr.Configuration;
import jdk.jfr.Recording;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 创建一次飞行记录
 */
public class StartJFRTest {

    @Test
    @SneakyThrows
    public void testStart() {
        // 使用${java.home}/jre/lib/jfr/default.jfc配置
        Configuration c = Configuration.getConfiguration("default");
        // 创建一次Recording, 开始后结束
        Recording r = new Recording(c);
        r.start();
        r.stop();

        Path path = Files.createTempFile("recording", ".jfr");
        r.dump(path);
    }
}
