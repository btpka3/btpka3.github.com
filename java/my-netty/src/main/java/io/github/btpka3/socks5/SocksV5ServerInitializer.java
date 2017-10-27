package io.github.btpka3.socks5;

import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.handler.codec.socksx.*;
import io.netty.handler.logging.*;


public final class SocksV5ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new LoggingHandler(LogLevel.DEBUG),
                new SocksPortUnificationServerHandler(),
                SocksV5ServerHandler.INSTANCE);
    }
}
