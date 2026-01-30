package me.test.jdk.java.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.test.jdk.java.net.socket.BioEchoServer;

/**
 * AIO: 异步非阻塞模型
 * <p>
 * 大多数公司并未使用AIO，why？而是使用netty？
 * AIO底层使用的仍然是 Epoll,相比 NIO，优势不大。
 * AIO编程复杂度较高。
 * Linux上的AIO不成熟？
 * netty在 nio上做了很多异步的封装，是异步非阻塞框架。
 *
 * @see NioEchoServer
 * @see BioEchoServer
 * @see <a href="https://blog.csdn.net/qq_45076180/article/details/112698579">深入理解BIO、NIO、AIO线程模型</a>
 * @see <a href="https://gist.github.com/ochinchina/72cc23220dc8a933fc46">EchoServer.java</a>
 */
@Slf4j
public class AioEchoServer {

    public static void main(String[] args) throws Exception {
        //创建服务端
        final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
                .bind(new InetSocketAddress(9999));


        //使用CompletionHandler异步处理客户端连接
        serverSocket.accept(serverSocket, new MyServerCompletionHandler());

        log.info("==== start");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class MyServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {
        @Override
        public void completed(AsynchronousSocketChannel socket, AsynchronousServerSocketChannel serverSocket) {
            try {
                log.info("==== serverChannel:completed");
                // 在此接收客户端连接，如果不写这行代码后面的客户端连接连不上服务端
                serverSocket.accept(serverSocket, this);
                log.info("==== serverChannel:accepted");


                ByteBuffer buffer = ByteBuffer.allocate(1024);
                CompletionHandler<Integer, ByteBuffer> socketHandler = new MySocketCompletionHandler(socket);
                //使用CompletionHandler异步读取数据
                socket.read(buffer, buffer, socketHandler);
            } catch (Exception e) {
                log.error("completed_ERR", e);
            }
        }

        @Override
        public void failed(Throwable e, AsynchronousServerSocketChannel attachment) {
            log.error("failed_ERR", e);
        }
    }

    public static class MySocketCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private final AsynchronousSocketChannel socket;

        public MySocketCompletionHandler(AsynchronousSocketChannel socket) {
            this.socket = socket;
        }

        @SneakyThrows
        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            log.info("===== socketChannel:completed:{}", result);
            buffer.flip();

            String str = new String(buffer.array(), 0, result);
            log.info("===== socketChannel:read:{}", str);

            // 回显
            {
                String echoStr = str;
                int dotIndex = str.indexOf('.');
                if (dotIndex == -1) {
                    socket.write(ByteBuffer.wrap(echoStr.getBytes(StandardCharsets.UTF_8)));
                    // 继续读取
                    socket.read(buffer, buffer, this);
                } else {
                    echoStr = str.substring(0, dotIndex) + "\n";
                    socket.write(ByteBuffer.wrap(echoStr.getBytes(StandardCharsets.UTF_8)));
                    socket.close();
                }
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
            exc.printStackTrace();
        }
    }
}
