package me.test.jdk.java.util;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Base64;

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
}
