package io.github.btpka3.netty.socks5.server;

import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.handler.codec.socksx.*;
import io.netty.handler.logging.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;


@Component
public final class SocksV5ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private SocksV5ServerHandler socksV5ServerHandler;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new LoggingHandler(LogLevel.DEBUG),
                new SocksPortUnificationServerHandler(),
                socksV5ServerHandler
        );
    }
}
