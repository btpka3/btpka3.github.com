package me.test.jdk.java.nio;

import lombok.extern.slf4j.Slf4j;
import me.test.jdk.java.net.socket.BioEchoServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * NIO: 同步非阻塞模型
 *
 * `nc localhost 9999` 然后 输入 "abc" 换行；如果字符串中包含 '.' 则关闭连接, '.' 之后的字符将不回显。
 * 仅支持 ASCII 字符（单字节）。
 *
 * @see BioEchoServer
 * @see AioEchoServer
 * @see <a href="https://blog.csdn.net/qq_45076180/article/details/112698579">深入理解BIO、NIO、AIO线程模型</a>
 */
@Slf4j
public class NioEchoServer {

    public static void main(String[] args) {
        Manager m = new Manager();
        m.run();
    }

    private static class Manager implements Runnable {

        @Override
        public void run() {

            try {
                Selector selector = Selector.open();
                // 演示绑定到多个IP地址、多个端口上
                {
                    ServerSocketChannel channel = ServerSocketChannel.open();
                    channel.configureBlocking(false);
                    ServerSocket serverSocket = channel.socket();
                    serverSocket.bind(new InetSocketAddress("localhost", 9999));
                    channel.register(selector, SelectionKey.OP_ACCEPT);
                }
                {
                    ServerSocketChannel channel = ServerSocketChannel.open();
                    channel.configureBlocking(false);
                    ServerSocket serverSocket = channel.socket();
                    serverSocket.bind(new InetSocketAddress("localhost", 9997));
                    channel.register(selector, SelectionKey.OP_ACCEPT);
                }
                {
                    ServerSocketChannel channel = ServerSocketChannel.open();
                    channel.configureBlocking(false);
                    // 增加一个 lookback 回环地址
                    // sudo ifconfig lo0 alias 127.0.0.2 up
                    ServerSocket serverSocket = channel.socket();
                    serverSocket.bind(new InetSocketAddress("127.0.0.2", 9998));
                    channel.register(selector, SelectionKey.OP_ACCEPT);
                }


                while (true) {

                    // blocking for accept： 返回有多少个 selectedKey 就绪
                    selector.select();

                    // 遍历已就绪的 SelectionKey
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey curKey = it.next();
                        it.remove();

                        log.info("SelectionKey_EVENT: [" + curKey + "] "
                                + ": valid = " + curKey.isValid()
                                + ", acceptable = " + curKey.isAcceptable()
                                + ", connectable = " + curKey.isConnectable()
                                + ", readable = " + curKey.isReadable()
                                + ", writeable = " + curKey.isWritable()
                        );
                        if (!curKey.isValid()) {
                            continue;
                        }
                        // 新连接接入
                        if (curKey.isAcceptable()) {
                            accept(curKey);
                        }
                        if (curKey.isReadable()) {
                            read(curKey);
                        }
                    }
                }
            } catch (IOException e) {
                log.error("Manager_ERR", e);
            }
        }

        private void accept(SelectionKey key) throws IOException {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(key.selector(), SelectionKey.OP_READ);
            log.info("accept connection : " + clientChannel.socket());
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private void read(SelectionKey curKey) {
            // 当有消息时，需要直到构建一个完整的业务数据封包（这里是一个UTF-8编码的单个字符），才传递个一个新的Worker进行处理。

            SocketChannel clientChannel = (SocketChannel) curKey.channel();
            try {
                // CASE1: 断开连接(客户端主动端口，网络不稳定等）
                if (!clientChannel.isConnected()) {
                    clientChannel.close();
                    log.info("when read, client site disconnected. curKey={}", curKey);
                    // CASE2: 服务端因停机等场景主动关闭。
                } else if (!clientChannel.isOpen()) {
                    log.info("when read, server side closed. curKey={}", curKey);
                } else {
                    SelectionKey regKey = clientChannel.keyFor(curKey.selector());
                    log.info("check_regKey: same={}, curKey={}, regKey = {}", curKey == regKey, curKey, regKey);

                    Map holders = (Map) regKey.attachment();
                    ByteBuffer readByteBuf;
                    ByteBuffer writeByteBuf;
                    if (holders == null) {
                        holders = new HashMap();
                        readByteBuf = ByteBuffer.allocate(32);
                        holders.put("readByteBuf", readByteBuf);

                        writeByteBuf = ByteBuffer.allocate(32);
                        holders.put("writeByteBuf", writeByteBuf);

                        regKey.attach(holders);
                    } else {
                        readByteBuf = (ByteBuffer) holders.get("readByteBuf");
                        writeByteBuf = (ByteBuffer) holders.get("writeByteBuf");
                    }

                    int i = clientChannel.read(readByteBuf);
                    if (i != 0) {
                        log.info("read_count={}", i);
                        readByteBuf.flip();

                        while (readByteBuf.hasRemaining()) {

                            byte c = readByteBuf.get();
                            // 由于 '.' 不是最后一个字符，后面至少还换行符，故会先close，此时就不能再回显了。
                            if (clientChannel.isOpen()) {
                                // 回显给客户端
                                clientChannel.write(ByteBuffer.wrap(new byte[]{c}));
                                //clientChannel.write(ByteBuffer.wrap(String.valueOf(c).getBytes(StandardCharsets.UTF_8)));
                            }
                            // 判断是否需要结束
                            if ('.' == c) {
                                log.info("read terminal command '.' : closing.~~ {}", regKey);
                                clientChannel.write(ByteBuffer.wrap(new byte[]{'\n'}));
                                clientChannel.socket().close();
                                clientChannel.close();
                            }
                        }
                        readByteBuf.compact();
                    }

                    // CASE2：到达流的末尾？？？可能么？
                    if (i == -1) {
                        if (clientChannel.isConnected()) {
                            clientChannel.close();
                            log.info("when read, reach end, closing.");
                        }
                    }
                }
            } catch (IOException e) {
                if (clientChannel.isConnected()) {
                    try {
                        clientChannel.close();
                    } catch (IOException e1) {
                        log.error("clientChannel_close_ERR", e);
                    }
                }
                log.error("write_ERR", e);
            }
        }
    }
}
