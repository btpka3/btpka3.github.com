package io.github.btpka3.first.async.profiler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class FirstAsyncProfilerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FirstAsyncProfilerApplication.class, args);
        applicationContext.registerShutdownHook();
    }

    @PreDestroy
    public void onExit() {
        log.info("###STOPing###");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            log.error("", e);;
        }
        log.info("###STOP FROM THE LIFECYCLE###");
    }
}
