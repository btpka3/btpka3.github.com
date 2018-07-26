package com.github.btpka3.demo.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoWarApp {

    public static void main(String[] args) {

        ConfigurableApplicationContext appCtx = SpringApplication.run(DemoWarApp.class, args);
    }

}
