package com.github.btpka3.first.embabel;

import com.github.btpka3.first.embabel.mcp.EchoMcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstEmbabelApplication {

    public static void main(String[] args) {
        for (String arg : args) {
            if ("--echo-server".equals(arg)) {
                try {
                    EchoMcpServer.main(new String[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
        SpringApplication.run(FirstEmbabelApplication.class, args);
    }
}
