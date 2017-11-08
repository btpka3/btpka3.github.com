package io.github.btpka3.netty.socks5.server;

import io.netty.buffer.*;
import io.netty.channel.*;

public class SocksV5ServerUtils {


    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    public static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private SocksV5ServerUtils() { }
}
