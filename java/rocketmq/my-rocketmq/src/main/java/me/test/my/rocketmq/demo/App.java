package me.test.my.rocketmq.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author dangqian.zll
 * @date 2023/7/18
 */
@EnableConfigurationProperties(Props.class)
@Slf4j
@SpringBootApplication
public class App {
    @SneakyThrows
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        System.out.println("================== DemoBootApplication started.");
        Thread.sleep(10 * 60 * 1000);
    }
}
