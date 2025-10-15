package me.test.jdk.java.net;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/11
 * @see Proxy
 * @see ProxySelector
 * @see sun.net.spi.DefaultProxySelector#props
 * @see java.net.Authenticator
 * @see java.net.SocksSocketImpl#authenticate
 * @see java.net.Socket
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/net/proxies.html">Java Networking and Proxies</a>
 * @see <a href="https://www.dannyvanheumen.nl/post/proxy-support-for-java-socketchannel/">Proxy support for Java SocketChannel</a>
 * @see <a href="https://github.com/cobratbq/ProxiedSocketChannel/blob/master/src/main/java/nl/dannyvanheumen/nio/ProxiedSocketChannel.java">ProxiedSocketChannel.java</a>
 * @see <a href="https://bugs.openjdk.org/browse/JDK-8199457">JDK-8199457: add proxy support in NIO</a>
 */
public class ProxyTest {

    public void x() {


        // 设置代理服务器地址和端口
        InetSocketAddress proxyAddress = new InetSocketAddress("proxy.example.com", 8080);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);

        // case 1: 通过 Socket 使用代理
        {
            Socket socket = new Socket(proxy);
        }



        ProxySelector.getDefault();
        // 设置代理选择器
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                return List.of();
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

            }
        });
    }


    @SneakyThrows
    public void useSocksProxy1() {

        // 使用SOCKS代理
        SocketAddress addr = new InetSocketAddress("127.0.0.2", 1080);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);

        URL url = new URL("http://java.example.org/");
        URLConnection conn = url.openConnection(proxy);
        // TODO
    }


    /**
     * Apache HttpComponents 关于代理的相关文档
     *
     * @see <a href="https://hc.apache.org/">Apache HttpComponents</a>
     * @see <a href="https://hc.apache.org/httpcomponents-client-5.5.x/examples.html#">HttpClient Examples (Classic)</a>
     * @see <a href="https://github.com/apache/httpcomponents-client/blob/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientExecuteProxy.java">Examples : Request via a proxy</a>
     * @see <a href="https://github.com/apache/httpcomponents-client/blob/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientProxyAuthentication.java">Examples : Proxy authentication</a>
     */
    void xss() {

    }
}


