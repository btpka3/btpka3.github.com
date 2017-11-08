package io.github.btpka3.netty.socks5;

import io.github.btpka3.netty.socks5.server.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(scanBasePackages = "io.github.btpka3.netty.socks5")
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
        SocksV5Server.start(args);
    }

}
