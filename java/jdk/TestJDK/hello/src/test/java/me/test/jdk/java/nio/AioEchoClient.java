package me.test.jdk.java.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/11
 */
@Slf4j
public class AioEchoClient {

    static CountDownLatch writeDone = new CountDownLatch(1);

    @SneakyThrows
    public static void main(String[] args) {
        AsynchronousSocketChannel socket = AsynchronousSocketChannel.open();

        socket.connect(new InetSocketAddress("localhost", 9999), socket, new MyConnectHandler());

        log.info("====== started");
        writeDone.await();
        log.info("====== finished");
        // 等待1秒，以便将回显的消息能显示出来
        Thread.sleep(1000);
    }

    @Slf4j
    public static class MyReadHandler implements CompletionHandler<Integer, ByteBuffer> {
        AsynchronousSocketChannel socket;

        public MyReadHandler(AsynchronousSocketChannel socket) {
            this.socket = socket;
        }

        @Override
        public void completed(Integer result, ByteBuffer buf) {
            if (result <= 0) {
                return;
            }
            buf.flip();
            String msg = new String(buf.array(), 0, result, StandardCharsets.UTF_8);
            log.info("receive message : length={}, msg={}", result, msg);
            // 继续读取
            if (socket.isOpen()) {
                socket.read(buf, buf, this);
            }
        }

        @SneakyThrows
        @Override
        public void failed(Throwable e, ByteBuffer channel) {
            log.error("fail to read message from server", e);
            writeDone.countDown();
        }
    }

    @Slf4j
    public static class MyWriteHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {
        AsynchronousSocketChannel socket;

        public MyWriteHandler(AsynchronousSocketChannel socket) {
            this.socket = socket;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel channel) {
            log.info("write message finished: length={}", result);
        }

        @SneakyThrows
        @Override
        public void failed(Throwable e, AsynchronousSocketChannel channel) {
            log.error("Fail to write the message to server", e);
            writeDone.countDown();
        }
    }

    @Slf4j
    public static class MyConnectHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {

        @Override
        public void completed(Void result, AsynchronousSocketChannel socket) {

            // 开监听 echo server 回显的数据
            final ByteBuffer buf = ByteBuffer.allocate(2048);
            CompletionHandler<Integer, ByteBuffer> readHandler = new MyReadHandler(socket);
            socket.read(buf, buf, readHandler);

            // 模拟多次主动写入(给 echo server 发送数据）
            CompletionHandler<Integer, AsynchronousSocketChannel> writeHandler = new MyWriteHandler(socket);
            write(socket, "hello", writeHandler);
            write(socket, "world", writeHandler);
            // 包含'.' 会结束
            write(socket, "aaa.bbb", writeHandler);
            writeDone.countDown();
        }

        @Override
        public void failed(Throwable e, AsynchronousSocketChannel channel) {
            log.error("fail to connect to server", e);
            writeDone.countDown();
        }

        private void write(final AsynchronousSocketChannel socket, final String message, CompletionHandler<Integer, AsynchronousSocketChannel> writeHandler) {
            ByteBuffer buf = ByteBuffer.allocate(2048);
            buf.put(message.getBytes(StandardCharsets.UTF_8));
            buf.flip();
            socket.write(buf, socket, writeHandler);
        }

    }
}
