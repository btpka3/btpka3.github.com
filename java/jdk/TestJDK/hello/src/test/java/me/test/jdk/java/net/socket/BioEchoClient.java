package me.test.jdk.java.net.socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class BioEchoClient {

    @SneakyThrows
    public static void main(String[] args) {

        // 使用SOCKS代理：方式1：配置全局JVM参数: `-DsocksProxyHost=192.168.1.2 -DsocksProxyPort=1080`， 缺点是要么全部用代理，要么全部不用代理，不够灵活。
        log.info("============== sysProps[{}] = {}.", "socksProxyHost", System.getProperty("socksProxyHost"));
        log.info("============== sysProps[{}] = {}.", "socksProxyPort", System.getProperty("socksProxyPort"));
        log.info("============== sysProps[{}] = {}.", "socksNonProxyHosts", System.getProperty("socksNonProxyHosts"));

        // 使用SOCKS代理：方式2：不配置JVM参数，通过编程式 new Socket(Proxy proxy) ，缺点是需要修改源码

        // 使用SOCKS代理：方式3：ProxySelector.setDefault(ProxySelector ps) 全局设置自定义 ProxySelector。
        // sun.net.spi.DefaultProxySelector
        ProxySelector proxySelector = ProxySelector.getDefault();
        log.info("============== default proxy selector = {}.", proxySelector);

        Socket socket = new Socket("192.168.1.3", 9999);

        // 使用SOCKS代理，方式1: 通过JVM参数全局使用: `-DsocksProxyHost=127.0.0.2 -DsocksProxyPort=1080`

        new Thread(new MyReadHandler(socket)).start();
        new Thread(new MyWriteHandler(socket)).start();
        log.info("STARTED.");
        Thread.sleep(3000);
        log.info("FINISHED.");
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
}
