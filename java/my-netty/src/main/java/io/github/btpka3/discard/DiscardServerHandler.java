package io.github.btpka3.discard;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.*;

/**
 *
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 模式一: 丢弃所有内容
     */
    private void mode1(ChannelHandlerContext ctx, Object msg) {
        ((ByteBuf) msg).release(); // (3)
    }

    /**
     * 模式一: 控制台打印所有消息（不支持中文）
     */
    private void mode2(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    /**
     * 模式三: echo server（不支持中文）
     */

    private void mode3(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        //mode1(ctx, msg);
        //mode2(ctx, msg);
        mode3(ctx, msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
