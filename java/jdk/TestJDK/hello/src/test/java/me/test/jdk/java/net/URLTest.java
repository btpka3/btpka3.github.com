package me.test.jdk.java.net;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class URLTest {

    @Test
    public void test() throws MalformedURLException {
        URL url1 = new URL("file:/Users/zll/.sdkman/candidates/java/17.0.3-tem/");
        URL url2 = new URL("file:/Users/zll/.sdkman/candidates/java/17.0.3-tem/");
        Assertions.assertEquals(url1, url2);
    }
}
