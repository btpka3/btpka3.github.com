package me.test.jdk.java.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class SystemTest {

    @Test
    public void testJavaHome() throws MalformedURLException {
        String javaHome = System.getProperty("java.home");
        System.out.println("javaHome = " + javaHome);
        File f = new File(javaHome);
        String uriStr = f.toURI().toString();
        System.out.println("uriStr = " + uriStr);
        String urlStr = f.toURI().toURL().toString();
        System.out.println("urlStr = " + urlStr);
    }

    @Test
    public void testClassPath() {
        String cp = System.getProperty("java.class.path");
        System.out.println("===== testClassPath : " + cp);
        String[] arr = cp.split("[;:,]");
        for (String s : arr) {
            if (StringUtils.isNotBlank(s)) {
                System.out.println(s);
            }
        }
    }

    @Test
    public void testEnv() {
        Map<String, String> envMap = System.getenv();
        String v = envMap.get("A-A");
        Assertions.assertNull(v);
        try {
            envMap.put("A-A", "xxx");
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof UnsupportedOperationException);
        }
    }
}
