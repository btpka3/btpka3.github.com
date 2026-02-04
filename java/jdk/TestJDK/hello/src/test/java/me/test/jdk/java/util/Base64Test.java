package me.test.jdk.java.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/12
 */
public class Base64Test {

    @Test
    public void base642hex() {
        String base64Str = System.getenv("BASE64");
        Assertions.assertTrue(StringUtils.isNotBlank(base64Str), "please set env \"BASE64\"");
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        System.out.printf("=========");
        System.out.println(Hex.encodeHex(bytes));
        System.out.printf("=========");
    }

    @Test
    public void x1() throws IOException {
        File file = Paths.get(System.getProperty("user.home"), "Downloads", "long_text_2025-05-19-11-07-47.txt")
                .toFile();
        String base64Str = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        // byte []bytes = Base64.getDecoder().decode(base64Str);
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64Str);
        String result = new String(bytes, StandardCharsets.UTF_8);
        System.out.printf(result);
    }

    @Test
    public void dd() {
        String s = StringUtils.repeat("hello world~", 1024);
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        {
            System.out.println("===============111");
            String base64Str = Base64.getEncoder().encodeToString(bytes);
            System.out.println(base64Str);
            String s2 = new String(Base64.getDecoder().decode(base64Str), StandardCharsets.UTF_8);
            Assertions.assertEquals(s, s2);
        }
        {
            System.out.println("===============222");
            String base64Str = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
            System.out.println(base64Str);
        }
    }
}
