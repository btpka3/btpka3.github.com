package me.test.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDomainSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/15
 */
public class DomainSocketChannelTest {

    @Test
    @SneakyThrows
    public void sendHttpGet1() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new DefaultEventLoop();
        try {
            bootstrap
                    .group(group)
                    .channel(EpollDomainSocketChannel.class)
                    .handler(new NettyEchoServer.MyEchoServerHandler());
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
}
