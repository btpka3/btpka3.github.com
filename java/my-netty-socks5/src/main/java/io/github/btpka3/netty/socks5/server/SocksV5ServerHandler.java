package io.github.btpka3.netty.socks5.server;

import io.netty.channel.*;
import io.netty.handler.codec.socksx.*;
import io.netty.handler.codec.socksx.v4.*;
import io.netty.handler.codec.socksx.v5.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Component
@ChannelHandler.Sharable
public final class SocksV5ServerHandler extends SimpleChannelInboundHandler<SocksMessage> {

    @Autowired
    private Props props;

    private boolean isPasswordCorrect(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return false;
        }
        if (props == null || props.getUsers() == null) {
            return false;
        }
        for (Props.User u : props.getUsers()) {
            if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private SocksV5ServerHandler() {
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, SocksMessage socksRequest) throws Exception {
        switch (socksRequest.version()) {
            case SOCKS4a:
                Socks4CommandRequest socksV4CmdRequest = (Socks4CommandRequest) socksRequest;
                if (socksV4CmdRequest.type() == Socks4CommandType.CONNECT) {
                    ctx.pipeline().addLast(new SocksV5ServerConnectHandler());
                    ctx.pipeline().remove(this);
                    ctx.fireChannelRead(socksRequest);
                } else {
                    ctx.close();
                }
                break;
            case SOCKS5:
                if (socksRequest instanceof Socks5InitialRequest) {
                    // auth support example
                    ctx.pipeline().addFirst(new Socks5PasswordAuthRequestDecoder());
                    ctx.write(new DefaultSocks5InitialResponse(Socks5AuthMethod.PASSWORD));
//                    ctx.pipeline().addFirst(new Socks5CommandRequestDecoder());
//                    ctx.write(new DefaultSocks5InitialResponse(Socks5AuthMethod.NO_AUTH));
                } else if (socksRequest instanceof Socks5PasswordAuthRequest) {

                    ctx.pipeline().addFirst(new Socks5CommandRequestDecoder());

                    String username = ((Socks5PasswordAuthRequest) socksRequest).username();
                    String password = ((Socks5PasswordAuthRequest) socksRequest).password();

                    if (isPasswordCorrect(username, password)) {
                        ctx.write(new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.SUCCESS));
                    } else {
                        ctx.write(new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.FAILURE));
                    }

                } else if (socksRequest instanceof Socks5CommandRequest) {
                    Socks5CommandRequest socks5CmdRequest = (Socks5CommandRequest) socksRequest;
                    if (socks5CmdRequest.type() == Socks5CommandType.CONNECT) {
                        ctx.pipeline().addLast(new SocksV5ServerConnectHandler());
                        ctx.pipeline().remove(this);
                        ctx.fireChannelRead(socksRequest);
                    } else {
                        ctx.close();
                    }
                } else {
                    ctx.close();
                }
                break;
            case UNKNOWN:
            default:
                ctx.close();
                break;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        throwable.printStackTrace();
        SocksV5ServerUtils.closeOnFlush(ctx.channel());
    }
}