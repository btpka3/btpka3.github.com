package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.builder.ParentContextApplicationContextInitializer;
import org.springframework.boot.context.event.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.*;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试 使用 @EventListener 注解。
 *
 * @author dangqian.zll
 * @date 2025/1/15
 */
@SpringBootTest(
        classes = SpringEvent2Test.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class SpringEvent2Test {
    static List<String> events = new ArrayList<>(64);


    @Configuration
    public static class Conf {
        @Bean
        MyEventListener myEventListener() {
            return new MyEventListener();
        }
    }

    @Autowired
    MyEventListener myEventListener;

    @Test
    public void test() {
        System.out.println("====================" + events.size());
        System.out.println("events=" + JSON.toJSONString(events, true));
    }


    /**
     *
     */
    public static class MyEventListener {


        @EventListener
        public void onEvent(ApplicationStartingEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }


        @EventListener
        public void onEvent(ApplicationEnvironmentPreparedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }


        @EventListener
        public void onEvent(ApplicationContextInitializedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ApplicationStartedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(AvailabilityChangeEvent event) {
            events.add("@EventListener," + event.getClass().getName() + "," + event.getState());
        }

        @EventListener
        public void onEvent(ApplicationReadyEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ApplicationPreparedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ApplicationFailedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(BootstrapContextClosedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ParentContextApplicationContextInitializer.ParentContextAvailableEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(JobExecutionEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(WebServerInitializedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(PayloadApplicationEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ContextClosedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ContextRefreshedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ContextStoppedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }

        @EventListener
        public void onEvent(ContextStartedEvent event) {
            events.add("@EventListener," + event.getClass().getName());
        }
    }

    /**
     * SPI: SpringApplicationRunListener
     */
    public static class MySpringApplicationRunListener implements SpringApplicationRunListener {

        SpringApplication springApplication;
        String[] args;

        public MySpringApplicationRunListener(SpringApplication springApplication, String[] args) {
            this.springApplication = springApplication;
            this.args = args;
        }

        @Override
        public void starting(ConfigurableBootstrapContext bootstrapContext) {
            events.add("SpringApplicationRunListener," + "starting");
        }

        @Override
        public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
            events.add("SpringApplicationRunListener," + "environmentPrepared");
        }

        @Override
        public void contextPrepared(ConfigurableApplicationContext context) {
            events.add("SpringApplicationRunListener," + "contextPrepared");
        }

        @Override
        public void contextLoaded(ConfigurableApplicationContext context) {
            events.add("SpringApplicationRunListener," + "contextLoaded");
        }

        @Override
        public void started(ConfigurableApplicationContext context, Duration timeTaken) {
            events.add("SpringApplicationRunListener," + "started");
        }

        @Override
        public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
            events.add("SpringApplicationRunListener," + "ready");
        }

        @Override
        public void failed(ConfigurableApplicationContext context, Throwable exception) {
            events.add("SpringApplicationRunListener," + "failed");
        }
    }


    /**
     * SPI: ApplicationListener
     */
    public static class MyApplicationListenerApplicationStartingEvent implements ApplicationListener<ApplicationStartingEvent> {
        @Override
        public void onApplicationEvent(ApplicationStartingEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerApplicationEnvironmentPreparedEvent implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
        @Override
        public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerApplicationContextInitializedEvent implements ApplicationListener<ApplicationContextInitializedEvent> {
        @Override
        public void onApplicationEvent(ApplicationContextInitializedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerApplicationStartedEvent implements ApplicationListener<ApplicationStartedEvent> {
        @Override
        public void onApplicationEvent(ApplicationStartedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerAvailabilityChangeEvent implements ApplicationListener<AvailabilityChangeEvent> {
        @Override
        public void onApplicationEvent(AvailabilityChangeEvent event) {
            events.add("ApplicationListener," + event.getClass().getName() + "," + event.getState());
        }
    }

    public static class MyApplicationListenerApplicationReadyEvent implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerApplicationPreparedEvent implements ApplicationListener<ApplicationPreparedEvent> {
        @Override
        public void onApplicationEvent(ApplicationPreparedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerApplicationFailedEvent implements ApplicationListener<ApplicationFailedEvent> {
        @Override
        public void onApplicationEvent(ApplicationFailedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerBootstrapContextClosedEvent implements ApplicationListener<BootstrapContextClosedEvent> {
        @Override
        public void onApplicationEvent(BootstrapContextClosedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerParentContextAvailableEvent implements ApplicationListener<ParentContextApplicationContextInitializer.ParentContextAvailableEvent> {
        @Override
        public void onApplicationEvent(ParentContextApplicationContextInitializer.ParentContextAvailableEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerJobExecutionEvent implements ApplicationListener<JobExecutionEvent> {
        @Override
        public void onApplicationEvent(JobExecutionEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerWebServerInitializedEvent implements ApplicationListener<WebServerInitializedEvent> {
        @Override
        public void onApplicationEvent(WebServerInitializedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerPayloadApplicationEvent implements ApplicationListener<PayloadApplicationEvent> {
        @Override
        public void onApplicationEvent(PayloadApplicationEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerContextClosedEvent implements ApplicationListener<ContextClosedEvent> {
        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerContextStoppedEvent implements ApplicationListener<ContextStoppedEvent> {
        @Override
        public void onApplicationEvent(ContextStoppedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }

    public static class MyApplicationListenerContextStartedEvent implements ApplicationListener<ContextStartedEvent> {
        @Override
        public void onApplicationEvent(ContextStartedEvent event) {
            events.add("ApplicationListener," + event.getClass().getName());
        }
    }


}
