package me.test.jdk.java.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * URL 解析
 * <p/>
 * 参考：
 * [RFC 3986: Uniform Resource Identifier (URI): Generic Syntax](http://tools.ietf.org/html/rfc3986#section-3.3)
 * [Parsing matrix URLs](http://www.w3.org/DesignIssues/MatrixURIs.html)
 * [URL matrix parameters vs. request parameters](http://stackoverflow.com/questions/2048121/url-matrix-parameters-vs-request-parameters)
 * [java.net.URL](http://docs.oracle.com/javase/8/docs/api/index.html?java/net/URL.html)
 */
public class ParseUrl {
    public static void main(String[] args) throws Exception {

        test1();
        test2();


    }

    public static void test1() throws MalformedURLException {
        System.out.println("-------------------------------------------------- test1");
        URL url = new URL("http://" +
                "zhang3:123456@" +
                "doc.kingsilk.xyz:" +
                "8080" +
                "/dep;id=1" +
                "/user;familyName=zhang,wang;joinDate=2015" +
                "?p1=111&p1=112&p2=22%2033" +
                "#/ccc/ddd?a=aaa&b=bbb#ccc");

        System.out.println("URL         : " + url);
        System.out.println("Protocol    : " + url.getProtocol());
        System.out.println("Authority   : " + url.getAuthority());
        System.out.println("UserInfo    : " + url.getUserInfo());
        System.out.println("Host        : " + url.getHost());
        System.out.println("Port        : " + url.getPort());
        System.out.println("DefaultPort : " + url.getDefaultPort());
        System.out.println("Path        : " + url.getPath());
        System.out.println("File        : " + url.getFile());
        System.out.println("Query       : " + url.getQuery());
        System.out.println("Ref         : " + url.getRef());
    }

    public static void test2() throws Exception {
        System.out.println("-------------------------------------------------- test2");

        URI uri = new URI("?p1=111&p1=112&p2=22%2033"); // 这里的 query 需要是已编码的
        //URI uri = new URI(null, null, null, "p1=111&p1=112&p2=22 33", null); // 这里的 query 需要是未编码的
        System.out.println("URI         : " + uri);
        System.out.println("Scheme      : " + uri.getScheme());
        System.out.println("Authority   : " + uri.getAuthority());
        System.out.println("UserInfo    : " + uri.getUserInfo());
        System.out.println("Host        : " + uri.getHost());
        System.out.println("Port        : " + uri.getPort());
        System.out.println("Path        : " + uri.getPath());
        System.out.println("RawQuery    : " + uri.getRawQuery());
        System.out.println("Query       : " + uri.getQuery()); // 注意，已经解码
    }
}
