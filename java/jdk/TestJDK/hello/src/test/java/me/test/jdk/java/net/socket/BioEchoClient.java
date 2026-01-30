package me.test.jdk.java.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class BioEchoClient {
    static final String PROXY_HOST = "192.168.1.2";
    static final int PROXY_PORT = 1080;

    static final String SERVER_HOST = "192.168.1.3";
    static final int SERVER_PORT = 9999;


    @SneakyThrows
    protected void startEchoClient(Socket socket) {
        new Thread(new MyReadHandler(socket)).start();
        new Thread(new MyWriteHandler(socket)).start();
        log.info("STARTED.");
        Thread.sleep(3000);
        log.info("FINISHED.");
    }

    /**
     * 使用SOCKS代理：方式1：配置全局JVM参数: `-DsocksProxyHost=192.168.1.2 -DsocksProxyPort=1080`.
     * 缺点是要么全部用代理，要么全部不用代理，不够灵活。
     */
    @SneakyThrows
    @Test
    public void testWithJvmProperties() {
        // 模拟通过JVM参数配置，需要确保该行实际是在网络访问前执行。
        System.setProperty("socksProxyHost", PROXY_HOST);
        System.setProperty("socksProxyPort", Integer.toString(PROXY_PORT));

        Assertions.assertTrue(StringUtils.isNotBlank(System.getProperty("socksProxyHost")), "JVM system properties -DsocksProxyHost=xxx is required");
        log.info("============== sysProps[{}] = {}.", "socksProxyHost", System.getProperty("socksProxyHost"));
        log.info("============== sysProps[{}] = {}.", "socksProxyPort", System.getProperty("socksProxyPort"));
        log.info("============== sysProps[{}] = {}.", "socksNonProxyHosts", System.getProperty("socksNonProxyHosts"));

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            startEchoClient(socket);
        }
    }

    /**
     * 使用SOCKS代理：方式2：不配置JVM参数，通过编程式 new Socket(Proxy proxy) ，缺点是需要修改源码
     */
    @SneakyThrows
    @Test
    public void testWithCustomProxy() {

        InetSocketAddress proxyAddress = new InetSocketAddress(PROXY_HOST, PROXY_PORT);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
        try (Socket socket = new Socket(proxy)) {
            InetSocketAddress dest = new InetSocketAddress(SERVER_HOST, SERVER_PORT);
            socket.connect(dest);
            startEchoClient(socket);
        }
    }


    /**
     * 使用SOCKS代理：方式3：ProxySelector.setDefault(ProxySelector ps) 全局设置自定义 ProxySelector。
     */
    @SneakyThrows
    @Test
    public void testWithProxySelector() {
        ProxySelector proxySelector = ProxySelector.getDefault();
        MyProxySelector myProxySelector = new MyProxySelector();
        ProxySelector.setDefault(myProxySelector);
        log.info("============== default proxy selector = {}, and has been change to {}", proxySelector, myProxySelector);
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            startEchoClient(socket);
        }
    }


    /**
     * Read user input and sent it to echo server.
     */
    @Slf4j
    private static class MyWriteHandler implements Runnable {

        private Socket socket;

        public MyWriteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                OutputStream out = socket.getOutputStream();
                write(out, "aaabbb");
                write(out, "111222");
                write(out, "xxx.yyy");

                Thread.sleep(200);
            } catch (Exception e) {
                log.error("WRITE_ERR", e);
            }
        }

        @SneakyThrows
        protected void write(OutputStream out, String msg) {
            out.write(msg.getBytes(StandardCharsets.UTF_8));
            out.flush();
            log.info(">>> '" + msg + "'");
        }

    }

    @Slf4j
    private static class MyReadHandler implements Runnable {

        private Socket socket;

        public MyReadHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()), StandardCharsets.UTF_8);
                int i;
                while ((i = reader.read()) != -1) {
                    char c = (char) i;
                    log.info("<<< '" + c + "'");
                }
            } catch (IOException e) {
                log.error("READ_ERR", e);
            }
        }
    }

    private static class MyProxySelector extends ProxySelector {
        private static final List<Proxy> NO_PROXY_LIST = List.of(Proxy.NO_PROXY);
        private static final List<Proxy> list = List.of(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(PROXY_HOST, PROXY_PORT)));

        @Override
        public List<Proxy> select(URI uri) {

            String scheme = uri.getScheme().toLowerCase();
            if (scheme.equals("socket") && uri.getHost().equals("192.168.1.3")) {
                return list;
            } else {
                return NO_PROXY_LIST;
            }
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            // NOOP
        }
    }
}
