package me.test.jdk.java.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/11
 */
@Slf4j
public class NioEchoClient {

    @SneakyThrows
    public static void main(String[] args) {

        // NIO 不支持代理
        log.info("============== sysProps[{}] = {}.", "socksProxyHost", System.getProperty("socksProxyHost"));
        log.info("============== sysProps[{}] = {}.", "socksProxyPort", System.getProperty("socksProxyPort"));
        log.info("============== sysProps[{}] = {}.", "socksNonProxyHosts", System.getProperty("socksNonProxyHosts"));


        InetSocketAddress hostAddress = new InetSocketAddress("192.168.1.3", 9999);
        SocketChannel sc = SocketChannel.open(hostAddress);
        Thread readThread;
        {
            readThread = new Thread(new ClientReader(sc));
            readThread.setName("READ_THREAD");
            readThread.start();
        }
        {
            sc.write(ByteBuffer.wrap("aaabbb".getBytes(StandardCharsets.UTF_8)));
            sc.write(ByteBuffer.wrap("111222".getBytes(StandardCharsets.UTF_8)));
            sc.write(ByteBuffer.wrap("xxx.yyy".getBytes(StandardCharsets.UTF_8)));
        }
        log.info("has send msg");
        // wait 1 second to finish read.
        Thread.sleep(1000);
        readThread.interrupt();
        log.info("======= finished");
    }

    /**
     * FIXME : NIO 如何使用 socks 代理？
     */
    public static SocketChannel wrapProxy(SocketChannel sc) {
        //return new MyProxiedSocketChannel(sc);
        return sc;
    }

    public static class ClientReader implements Runnable {
        ByteBuffer readByteBuf = ByteBuffer.allocate(32);
        SocketChannel sc;

        public ClientReader(SocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (!sc.isOpen() && !sc.isConnected()) {
                        log.info("clientChannel is closed, stop reading");
                        break;
                    }
                    int i = sc.read(readByteBuf);
                    if (i > 0) {
                        log.info("read_count={}", i);
                        if (readByteBuf.hasArray()) {
                            readByteBuf.flip();
                            byte[] arr = new byte[readByteBuf.remaining()];
                            readByteBuf.get(arr);
                            String msg = new String(arr);
                            log.info("READ : length={}, msg={}", msg.length(), msg);
                            readByteBuf.compact();
                        }
                    }
                }
            } catch (Exception e) {
                log.error("read_ERR", e);
            }
        }
    }

}
