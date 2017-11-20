package io.github.btpka3.netty.socks5;

import io.github.btpka3.netty.socks5.server.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;

@SpringBootApplication(scanBasePackages = "io.github.btpka3.netty.socks5")
public class App {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext appCtx = SpringApplication.run(App.class, args);
        appCtx.getBean(SocksV5Server.class).start(args);
    }

}
