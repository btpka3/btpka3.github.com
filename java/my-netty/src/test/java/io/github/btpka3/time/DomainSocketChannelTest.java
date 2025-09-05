package io.github.btpka3.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollDomainSocketChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.UnixChannel;
import io.netty.handler.codec.http.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;


public class DomainSocketChannelTest {

    private int port;


    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
//                                    .addLast(new TimeEncoder())
                                    .addLast(new TimeEncoder(), new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Test
    public void test01() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new DefaultEventLoop();
        try {
            bootstrap
                    .group(group)
                    .channel(EpollDomainSocketChannel.class)
                    .handler(new MyChannelHandler());
            final Channel channel = bootstrap
                    .connect(new DomainSocketAddress("/tmp/my-netty.sock"))
                    .sync()
                    .channel();

            final FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/services", Unpooled.EMPTY_BUFFER);
            request.headers().set(HttpHeaderNames.HOST, "daemon");
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class MyChannelHandler extends ChannelInitializer<UnixChannel> {
        @Override
        public void initChannel(UnixChannel ch) throws Exception {
            ch
                    .pipeline()
                    .addLast(new HttpClientCodec())
                    .addLast(new HttpContentDecompressor())
                    .addLast(new SimpleChannelInboundHandler<HttpObject>() {
                        private StringBuilder messageBuilder = new StringBuilder();

                        @Override
                        public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                            if (msg instanceof HttpContent) {
                                HttpContent content = (HttpContent) msg;
                                messageBuilder.append(content.content().toString(StandardCharsets.UTF_8));
                                if (msg instanceof LastHttpContent) {
                                    System.out.println(messageBuilder);
                                }
                            } else {
                                System.out.println(msg.getClass());
                            }
                        }
                    });
        }
    }
}
