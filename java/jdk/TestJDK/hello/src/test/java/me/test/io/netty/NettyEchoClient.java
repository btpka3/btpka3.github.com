package me.test.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.util.ServerUtil;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/14
 * @see io.netty.example.echo.EchoClient
 * @see io.netty.handler.proxy.ProxyHandler
 * @see io.netty.handler.proxy.Socks4ProxyHandler
 * @see io.netty.handler.proxy.Socks5ProxyHandler
 * @see io.netty.handler.proxy.HttpProxyHandler
 *
 */
@Slf4j
public class NettyEchoClient {

    static final String HOST = System.getProperty("host", "192.168.1.3");
    static final int PORT = Integer.parseInt(System.getProperty("port", "9999"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    static final boolean USING_PROXY = Boolean.valueOf(System.getProperty("usingProxy", "true"));
    static final String PROXY_HOST = System.getProperty("proxyHost", "192.168.1.2");
    static final int PROXY_PORT = Integer.parseInt(System.getProperty("proxyPort", "1080"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.git
        final SslContext sslCtx = ServerUtil.buildSslContext();

        // Configure the client.
        EventLoopGroup group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (USING_PROXY) {
                                log.info("using proxy : proxyHost={}, proxyPort={}", PROXY_HOST, PROXY_PORT);
                                p.addLast(new Socks5ProxyHandler(new InetSocketAddress(PROXY_HOST, PROXY_PORT)));
                            }
                            if (sslCtx != null) {
                                log.info("using SSL");
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            //p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new MyEchoClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public static class MyEchoClientHandler extends ChannelInboundHandlerAdapter {

        private final ByteBuf firstMessage;

        public MyEchoClientHandler() {
            firstMessage = Unpooled.buffer(NettyEchoClient.SIZE);
//            for (int i = 0; i < firstMessage.capacity(); i++) {
//                firstMessage.writeByte((byte) i);
//            }

            firstMessage.writeBytes(ByteBuffer.wrap("aaabbb".getBytes(StandardCharsets.UTF_8)));
            firstMessage.writeBytes(ByteBuffer.wrap("111222".getBytes(StandardCharsets.UTF_8)));
            firstMessage.writeBytes(ByteBuffer.wrap("xxx.yyy".getBytes(StandardCharsets.UTF_8)));
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(firstMessage);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ctx.write(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Close the connection when an exception is raised.
            cause.printStackTrace();
            ctx.close();
        }
    }
}
