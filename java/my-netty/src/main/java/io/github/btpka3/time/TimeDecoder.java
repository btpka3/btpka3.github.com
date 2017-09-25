package io.github.btpka3.time;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.*;

import java.util.*;

/**
 *
 */
public class TimeDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        //out.add(in.readBytes(4)); // (4)
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
