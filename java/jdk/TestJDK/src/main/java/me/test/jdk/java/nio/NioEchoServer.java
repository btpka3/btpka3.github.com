package me.test.jdk.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioEchoServer {

    public static void main(String[] args){
        new Thread(new Manager()).start();
    }

    private static class Manager implements Runnable {

        @Override
        public void run() {

            ServerSocketChannel channel;
            try {
                channel = ServerSocketChannel.open();

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

                        if (!curKey.isValid()) {
                            continue;
                        }

                        if (curKey.isAcceptable()) {
                            accept(curKey);
                        } else if (curKey.isReadable()) {
                            read(curKey);
                        }

                        if ((curKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) curKey.channel();
                            SocketChannel clientChannel = serverChannel.accept();
                            clientChannel.configureBlocking(false);
                            SelectionKey readKey = clientChannel.register(selector, SelectionKey.OP_READ);
                            it.remove();

                            // System.out.println("buffer: "+new String(buffer.array()));
                        } else if ((curKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) curKey.channel();
                            while (true) {
                                buffer.clear();
                                int i = socketChannel.read(buffer);

                                if (i == -1)
                                    break;

                                buffer.flip();
                                socketChannel.write(buffer);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private void accept(SelectionKey key) throws IOException {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);

            // register channel with selector for further IO
            // dataMap.put(clientChannel, new ArrayList<byte[]>());
            clientChannel.register(key.selector(), SelectionKey.OP_READ);
        }

        private ByteBuffer buffer = ByteBuffer.allocate(1024);

        private void read(SelectionKey key) throws IOException {
            SocketChannel socketChannel = (SocketChannel) key.channel();

            while (true) {
                buffer.clear();
                int i = socketChannel.read(buffer);

                if (i == -1) {
                    break;
                }

                buffer.flip();
                socketChannel.write(buffer);
            }

            // -----------------------------------------------------------
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);

            // register channel with selector for further IO
            // dataMap.put(clientChannel, new ArrayList<byte[]>());
            clientChannel.register(key.selector(), SelectionKey.OP_READ);

        }

    }

    private static class Worker implements Runnable {

        private SocketChannel channel;

        @Override
        public void run() {
            CharBuffer buf = CharBuffer.allocate(1024);
        }
    }
}
