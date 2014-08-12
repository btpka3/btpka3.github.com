package me.test.jdk.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NioEchoServer {

    public static void main(String[] args) {
        new Thread(new Manager()).start();
    }

    /**
     * Start server, regist Selector, loop selector and dispatch msg.
     */
    private static class Manager implements Runnable {

        private ExecutorService exec = null;

        @Override
        public void run() {

            try {
                ServerSocketChannel channel = ServerSocketChannel.open();

                channel.configureBlocking(false);
                channel.socket().bind(new InetSocketAddress("localhost", 9999));

                Selector selector = Selector.open();
                channel.register(selector, SelectionKey.OP_ACCEPT);

                while (true) {

                    // blocking for accept
                    selector.select();

                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey curKey = it.next();
                        it.remove();

                        System.out.println(curKey
                                + ": valid = " + curKey.isValid()
                                + ", acceptable = " + curKey.isAcceptable()
                                + ", connectable = " + curKey.isConnectable()
                                + ", readable = " + curKey.isReadable()
                                + ", writeable = " + curKey.isWritable()
                                );
                        if (!curKey.isValid()) {
                            continue;
                        }

                        if (curKey.isAcceptable()) {
                            accept(curKey);
                        } else if (curKey.isReadable()) {
                            read(curKey);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void accept(SelectionKey key) throws IOException {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(key.selector(), SelectionKey.OP_READ);
            System.out.println("accept connection : " + clientChannel.socket());
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private void read(SelectionKey curKey) {
            // 当有消息时，需要直到构建一个完整的业务数据封包（这里是一个UTF-8编码的单个字符），才传递个一个新的Worker进行处理。

            SocketChannel clientChannel = (SocketChannel) curKey.channel();
            try {
                // CASE1：断开连接
                if (!clientChannel.isConnected()) {
                    clientChannel.close();
                    System.out.println("when read, closed.");
                } else if (!clientChannel.isOpen()) {
                    System.out.println("when read, closed.");
                } else {

                    SelectionKey regKey = clientChannel.keyFor(curKey.selector());

                    Map holders = (Map) regKey.attachment();
                    ByteBuffer byteBuf;
                    CharBuffer charBuf;
                    CharsetDecoder cd;

                    if (holders == null) {
                        holders = new HashMap();
                        byteBuf = ByteBuffer.allocate(32);
                        holders.put("byteBuf", byteBuf);
                        charBuf = CharBuffer.allocate(2);
                        holders.put("charBuf", charBuf);

                        cd = StandardCharsets.UTF_8
                                .newDecoder()
                                // .onMalformedInput(CodingErrorAction.REPLACE)
                                .onUnmappableCharacter(CodingErrorAction.REPLACE);
                        holders.put("cd", cd);
                        regKey.attach(holders);
                    } else {
                        byteBuf = (ByteBuffer) holders.get("byteBuf");
                        charBuf = (CharBuffer) holders.get("charBuf");
                        cd = (CharsetDecoder) holders.get("cd");
                    }

                    // 该一回合，可能只读取到部分数据。不能尝试直到读取完毕所有数据，
                    // 否则，若client持续不断的发送数据，则会因此线程一直占有而无法为其他Client提供服务。
                    int i = clientChannel.read(byteBuf);
                    if (i != 0) {

                        byteBuf.flip();

                        // 将 byte[] 按照UTF-8的编码解析为一个个字符
                        while (true) {
                            charBuf.clear();

                            CoderResult cr = cd.decode(byteBuf, charBuf, i == -1 ? true : false);
                            System.out.println("i = " + i
                                    + ", cr " + cr
                                    + ", byteBuf=" + byteBuf
                                    + ", charBuf=" + charBuf);

                            if (cr.isMalformed()) {
                                byteBuf.position(byteBuf.position() + cr.length());
                                charBuf.put(cd.replacement());
                            }

                            // CASE3：正常读取
                            if (charBuf.position() > 0) {
                                charBuf.flip();

                                if (exec == null) {
                                    exec = new ThreadPoolExecutor(1, 2, 1, TimeUnit.MINUTES,
                                            new LinkedBlockingQueue<Runnable>());
                                }
                                for (int j = 0; j < charBuf.limit(); j++) {
                                    exec.execute(new Worker(charBuf.get(j), clientChannel));
                                    // new Worker(charBuf.get(j), clientChannel).run();
                                }
                            }

                            if (!cr.isError()) {
                                break;
                            }
                        }
                        byteBuf.compact();

                    }

                    // CASE2：到达流的末尾？？？可能么？
                    if (i == -1) {
                        if (clientChannel.isConnected()) {
                            clientChannel.close();
                            System.out.println("when read, reach end, closing.");
                        }
                    }
                }
            } catch (IOException e) {
                if (clientChannel.isConnected()) {
                    try {
                        clientChannel.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * 可以当作一个Servlet、Action、Controller etc。 处理一次用户消息。
     */
    private static class Worker implements Runnable {

        private SocketChannel channel;
        private char c;

        /**
         * 将指定的字符返回给客户端。
         *
         * @param c
         *            一个字符。真实场景下可能是一个业务数据封包。之后可能会做很多耗时、耗资源的操作。
         * @param channel
         */
        public Worker(char c, SocketChannel channel) {
            this.c = c;
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                if ('.' == c) {
                    System.out.println("read terminal command '.' : closing.~~" + channel.socket() + "\n");
                    // 要关闭socket，否则会Client不会会一直等下去。
                    channel.socket().close();
                    channel.close();
                } else {
                    channel.write(ByteBuffer.wrap(String.valueOf(c).getBytes("UTF-8")));
                    System.out.println(">>> '" + c + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
