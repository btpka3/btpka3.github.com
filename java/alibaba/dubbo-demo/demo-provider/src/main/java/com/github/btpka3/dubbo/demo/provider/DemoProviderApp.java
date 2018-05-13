package com.github.btpka3.dubbo.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoProviderApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(DemoProviderApp.class, args);

        Thread.sleep(10 * 60 * 1000);
    }
}
