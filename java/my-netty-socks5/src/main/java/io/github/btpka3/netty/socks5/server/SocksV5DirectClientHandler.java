package io.github.btpka3.netty.socks5.server;

import io.netty.channel.*;
import io.netty.util.concurrent.*;


public final class SocksV5DirectClientHandler extends ChannelInboundHandlerAdapter {

    private final Promise<Channel> promise;

    public SocksV5DirectClientHandler(Promise<Channel> promise) {
        this.promise = promise;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.pipeline().remove(this);
        promise.setSuccess(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        promise.setFailure(throwable);
    }
}
