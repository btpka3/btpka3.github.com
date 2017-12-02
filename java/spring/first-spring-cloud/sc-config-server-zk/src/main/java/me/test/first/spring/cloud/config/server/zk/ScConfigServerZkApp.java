package me.test.first.spring.cloud.config.server.zk;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class ScConfigServerZkApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigServerZkApp.class, args);
    }
}
