package me.test.reactor.netty.tcp;

import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

/**
 * @author dangqian.zll
 * @date 2022/12/23
 */
public class DemoTcpClient {

    public void x() {
        Connection connection = TcpClient.create()
                .connectNow();

        connection.onDispose()
                .block();
    }
}
