package me.test.jdk.java.net;

import lombok.SneakyThrows;

import java.io.Writer;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2025/7/24
 */
public class UnixDomainSocketAddressTest {

    @SneakyThrows
    public void test01() {
        SocketChannel channel = SocketChannel.open(StandardProtocolFamily.UNIX);
        channel.connect(UnixDomainSocketAddress.of("/var/run/dq.sock"));
        Writer writer = Channels.newWriter(channel, StandardCharsets.UTF_8);
        writer.write("hello");
    }
}
