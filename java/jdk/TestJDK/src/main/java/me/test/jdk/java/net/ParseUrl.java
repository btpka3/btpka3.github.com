package me.test.jdk.java.net;

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

        URL url = new URL("http://" +
                "zhang3:123456@" +
                "doc.kingsilk.xyz:" +
                "8080" +
                "/dep;id=1" +
                "/user;familyName=zhang,wang;joinDate=2015" +
                "?p1=111&p1=112&p2=222" +
                "#/ccc/ddd?a=aaa&b=bbb#ccc");

        System.out.println("URL         : " + url);
        System.out.println("--------------------------------------------------");
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
}
