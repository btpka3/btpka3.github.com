package io.github.btpka3.netty.socks5.server;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.logging.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class SocksV5Server {

    //static final int PORT = Integer.parseInt(System.getProperty("mns.port", "1080"));

    @Value("${mns.port: #{1080}}")
    private Integer port;

    @Autowired
    private SocksV5ServerInitializer socksV5ServerInitializer;


    public void start(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(socksV5ServerInitializer);
            b.bind(port).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
