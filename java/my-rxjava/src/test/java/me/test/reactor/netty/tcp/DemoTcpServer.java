package me.test.reactor.netty.tcp;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * @date 2022/12/23
 */
public class DemoTcpServer {

    @Test
    public void x() {
        DisposableServer server = TcpServer.create()
                .handle((inbound, outbound) -> {
                    // 打印消息
                    inbound.receive()
                            .asString()
                            .subscribe(System.out::println);
                    return outbound.sendString(Mono.just("hello"));
                })
                .bindNow();

        server.onDispose()
                .block();
    }
}
