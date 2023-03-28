package me.test.jdk.java.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *  测试 Channel
 */
public class ChannelTest {

    public static void main(String[] args) throws IOException {


        InputStream in = ChannelTest.class.getResourceAsStream("ChannelTest.txt");

        ReadableByteChannel inChannel = Channels.newChannel(in);

        ByteBuffer buf = ByteBuffer.allocate(48);

        while ( inChannel.read(buf) != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
        }

    }
}
