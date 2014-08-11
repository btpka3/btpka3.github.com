package me.test.jdk.java.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

// http://www.cnblogs.com/pingh/p/3224990.html
public class NioEchoClient {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 80));

        new Thread(new Manager(selector)).start();
    }

    private static class Manager implements Runnable {
        private Selector selector;

        public Manager(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {

            Thread userIn = null;
            Thread serverOut = null;

            try {

                while (!Thread.currentThread().isInterrupted()) {

                    // 阻塞，等待下一个可用的事件
                    int s = selector.select();
                    System.out.println(s + "============" + selector.selectedKeys().size());
                    // 遍历处理可用的事件
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {

                        SelectionKey curKey = it.next();
                        it.remove();

                        System.out.println("===curkey = " + curKey);

                        if (!curKey.isValid()) {
                            continue;
                        }

                        SocketChannel channel = (SocketChannel) curKey.channel();

                        // 连接相关的消息
                        if (curKey.isConnectable()) {

                            try {
                                System.out.println("~~~" + channel.isConnectionPending() + "," + channel.isConnected());
                                if (channel.finishConnect()) {
                                    System.out.println("Connected.");
                                    // 清除已经关注的 SelectionKey.OP_CONNECT，并重新注册新关注的KEY。
                                    channel.keyFor(selector).interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                                }
                                // if (userIn != null && userIn.isAlive()) {
                                // userIn.interrupt();
                                // }
                                // if (serverOut != null && serverOut.isAlive()) {
                                // serverOut.interrupt();
                                // }
                                // NOTICE: we could not detect disconnection without application level ACK
                            } catch (IOException e) {
                                System.err.println("Could not establish connection.");
                                e.printStackTrace();
                            }

                        }

                        // 写入相关的消息
                        if (curKey.isWritable()) {
                            userIn = new Thread(new UserMsgHander((SocketChannel) curKey.channel()));
                            userIn.start();
                        }

                        // 读取相关的消息
                        if (curKey.isReadable()) {
                            serverOut = new Thread(new EchoRespMsgHander());
                            serverOut.start();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将用户输入的消息编码为UTF-8，一秒一个字节的发送给Echo服务器。
     */
    private static class UserMsgHander implements Runnable {
        private SocketChannel socketChannel;

        public UserMsgHander(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {

            // 模拟的用户输入
            byte[] byteArr = new byte[] {
                    73, // 'I'
                    -26, -120, -111, // '我',
                    -26, // malformed
                    73, // 'I'
                    73 // 'I'
            };

            ByteBuffer byteBuf = ByteBuffer.allocate(1024);
            byteBuf.clear();
            try {
                for (byte b : byteArr) {
                    if (!socketChannel.isConnected()) {
                        break;
                    }
                    byteBuf.put(b);
                    byteBuf.flip();
                    // write message from ByteBuffer to echo server
                    socketChannel.write(byteBuf); // ??? handle Error ???
                    byteBuf.rewind();
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class EchoRespMsgHander implements Runnable {

        private Selector selector;
        private ByteBuffer byteBuf = ByteBuffer.allocate(128);
        private CharBuffer charBuf = CharBuffer.allocate(128);
        private CharsetDecoder cd = StandardCharsets.UTF_8
                .newDecoder()
                // .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);


        @Override
        public void run() {
            CharsetDecoder cd = StandardCharsets.UTF_8.newDecoder();
            try {
                while (true) {

                    if (selector.select() == 0) {
                        break;
                    }

                    Iterator<SelectionKey> it = selector.keys().iterator();
                    while (it.hasNext()) {
                        SelectionKey curKey = it.next();
                        it.remove();

                        if (curKey.isValid() && curKey.isReadable()) {
                            read(curKey);
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void read(SelectionKey curKey) throws IOException {

            SocketChannel socketChannel = (SocketChannel) curKey.channel();

            int i;
            do {
                i = socketChannel.read(byteBuf);
                if (i == -1) {
                    break;
                }
                byteBuf.flip();

                CoderResult cr;
                do {
                    charBuf.clear();
                    cr = cd.decode(byteBuf, charBuf, false);
                    if (cr.isMalformed()) {
                        byteBuf.position(byteBuf.position() + cr.length());
                        charBuf.put(cd.replacement());
                    }
                    if (charBuf.position() > 0) {
                        charBuf.flip();
                        System.out.println(charBuf);
                    }
                } while (cr.isError());

                byteBuf.compact();
            } while (i >= 0);
        }

    }
}
