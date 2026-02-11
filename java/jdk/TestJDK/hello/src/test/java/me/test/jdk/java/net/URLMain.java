package me.test.jdk.java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class URLMain {
    static String msg = "Hello from JDK 21 HTTP Server!";

    /**
     * 设置JVM最小内存参数，验证内存泄露情况（JDK 21）
     * 完整命令：
     * java -Xms8m -Xmx16m -XX:NewSize=4m -XX:MaxNewSize=8m -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=32m -XX:MaxDirectMemorySize=8m -XX:CompressedClassSpaceSize=8m -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC "-Xlog:gc*:file=gc.log:time,level,tags" me.test.jdk.java.net.URLMain
     */
    public static void main(String[] args) throws IOException {
        // 没有对 inputStream close 会造成内存泄露吗？
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            InputStream inputStream = new URL("http://localhost:8080/test").openStream();
            String str = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            assert msg.equals(str);
            if (i % 100000 == 0) {
                System.out.println(i);
            }
        }
    }

}