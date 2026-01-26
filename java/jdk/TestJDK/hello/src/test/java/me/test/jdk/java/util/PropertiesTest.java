package me.test.jdk.java.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) throws Exception {

        System.out.println("=======================1");
        // UTF8 读取
        Properties props1 = new Properties();
        props1.load(new InputStreamReader(
                PropertiesTest.class.getResourceAsStream("a.properties"),
                StandardCharsets.UTF_8
        ));
        System.out.println(props1);
        System.out.println("=======");
        StringWriter stringWriter = new StringWriter();
        props1.store(stringWriter, null);
        System.out.println(stringWriter.toString());

        System.out.println("=======================2");

        props1.load(PropertiesTest.class.getResourceAsStream("b.properties"));
        System.out.println(props1);
        props1.store(System.out, null);
    }

    @Test
    @SneakyThrows
    public void test01() {
        Properties props1 = new Properties();
        props1.put("zhang3", "张3");
        props1.put("li4", "李4");
        props1.put("wang5", "王5");

        //FileOutputStream outputStream = new FileOutputStream("/tmp/c.properteies");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        props1.store(outputStream, null);
        String str = outputStream.toString(StandardCharsets.UTF_8);

        System.out.println(outputStream.toString(StandardCharsets.UTF_8));

    }


}
