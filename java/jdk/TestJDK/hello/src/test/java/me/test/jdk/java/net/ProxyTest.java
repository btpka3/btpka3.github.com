package me.test.jdk.java.net;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/11
 */
public class ProxyTest {

    public void x() {
        // 设置代理服务器地址和端口
        InetSocketAddress proxyAddress = new InetSocketAddress("proxy.example.com", 8080);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);

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
}


