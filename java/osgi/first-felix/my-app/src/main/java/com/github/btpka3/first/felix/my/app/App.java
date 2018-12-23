package com.github.btpka3.first.felix.my.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = SpringApplication.run(App.class, args);
        System.out.println("my-app started");
    }
}
