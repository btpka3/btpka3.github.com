package me.test.first.spring.boot.test;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author dangqian.zll
 * @date 2020/9/21
 */
@SpringBootTest(
        classes = SpringEventTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@EnableAsync
@Slf4j
public class SpringEventTest {


    @Configuration
    public static class Conf {
        @Bean
        MyHandler xxx() {
            return new MyHandler();
        }

        @Bean
        MyAsyncHandler xxxy() {
            return new MyAsyncHandler();
        }

        @Bean
        ThreadPoolTaskExecutor taskExecutor(
        ) {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(1);
            executor.setMaxPoolSize(1);
            executor.setQueueCapacity(2);
            return executor;
        }
    }

    static void log(String msg) {
        System.out.println(System.currentTimeMillis() + " : " + Thread.currentThread().getName() + " : " + msg);
    }

    @Data
    @Builder
    public static class MyEvent {
        String name;
    }

    public static class MyHandler {

        @EventListener
        public void handle(MyEvent event) {
            log("myEvent = " + event);
        }
    }

    public static class MyAsyncHandler {

        @Async
        @EventListener
        public void handle(MyEvent event) throws InterruptedException {
            Thread.sleep(1000);
            log("myAsyncEvent = " + event);
        }
    }

    @Autowired
    private MyHandler myHandler;
    @Autowired
    private MyAsyncHandler myAsyncHandler;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void x() throws InterruptedException {
        log.info("lalala");
        try {
            for (int i = 0; i < 5; i++) {
                applicationEventPublisher.publishEvent(MyEvent.builder()
                        .name("zhang3")
                        .build());
            }
        } catch (TaskRejectedException err) {
            log("full");
            err.printStackTrace();
        }
        log("started");
        Thread.sleep(3000);
    }


}
