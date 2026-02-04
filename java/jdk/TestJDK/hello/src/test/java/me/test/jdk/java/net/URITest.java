package me.test.jdk.java.net;

import java.net.URI;

public class URITest {

    public static void main(String[] args) throws Exception {

        test01();
    }

    public static void test01() throws Exception {
        URI uri = new URI("my://name/aaa");
        System.out.println(uri);
        System.out.println("scheme      : " + uri.getScheme());
        System.out.println("host        : " + uri.getHost());
        System.out.println("port        : " + uri.getPort());
        System.out.println("path        : " + uri.getPath());
        System.out.println("fragment    : " + uri.getFragment());
    }
}
