package io.github.btpka3.time;

import io.netty.buffer.*;
import io.netty.channel.*;

/**
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)

        int t = (int) (System.currentTimeMillis() / 1000L + 2208988800L);

//        final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt(t);

        UnixTime time = new UnixTime(t);
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
