package me.test.jdk.java.lang;


import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

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
        String urlStr= f.toURI().toURL().toString();
        System.out.println("urlStr = " + urlStr);
    }
}
