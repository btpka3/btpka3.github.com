package com.github.btpka3.first.spring.boot.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;

import java.io.File;

/**
 * @author dangqian.zll
 * @date 2020-06-24
 * @see IntegrationAutoConfiguration
 * @see IntegrationProperties
 * @see <a href="https://docs.spring.io/spring-integration/docs/4.3.22.RELEASE/reference/htmlsingle/#configuration-enable-integration">Configuration and @EnableIntegration</a>
 * @see <a href="https://github.com/spring-projects/spring-integration-samples/blob/master/intermediate/file-processing/">file-processing</a>
 */
@SpringBootTest
@EnableIntegration
@IntegrationComponentScan
@EnablePublisher
@SpringBootConfiguration
public class MyTest {

    public static class Conf {

        @Bean
        @InboundChannelAdapter(
                channel = "myInputChannel",
                poller = @Poller(fixedRate = "1")
        )
        public MessageSource<File> fileReadingMessageSource() {
            FileReadingMessageSource source = new FileReadingMessageSource();
            source.setDirectory(new File("/tmp/srcDir"));
//            source.setFilter(new SimplePatternFileListFilter("*.txt"));
            return source;
        }

//
//        @Bean
//        FileReadingMessageSource s() {
//            FileReadingMessageSource s = new FileReadingMessageSource();
//            s.setDirectory(new File("/tmp/srcDir"));
//            return s;
//        }

        @Bean
        ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            return mapper;
        }
//
//        @Bean
//        MessageHandler myMsgHandler(ObjectMapper objectMapper) {
//            DirectChannel channel = new DirectChannel();
//            return channel;
//        }

//        @Bean
//        @ServiceActivator(inputChannel = "outputChannel")
//        public LoggingHandler loggingHandler() {
//            return new LoggingHandler("info");
//        }

        @Bean
        @ServiceActivator(inputChannel = "myInputChannel")
        MessageHandler myMsgHandler(ObjectMapper objectMapper) {
            return msg -> {
                try {
                    System.out.println("===== msg = " + objectMapper.writeValueAsString(msg) + ", " + msg.getPayload().getClass());
                } catch (JsonProcessingException e) {
                    System.err.println("error..." + e.getMessage());
                }
            };
        }
//
//        @Bean
//        DirectChannel directChannel(ObjectMapper objectMapper) {
//            DirectChannel c = new DirectChannel();
//            c.subscribe(msg -> {
//                try {
//                    System.out.println("===== msg = " + objectMapper.writeValueAsString(msg));
//                } catch (JsonProcessingException e) {
//                    System.err.println("error..." + e.getMessage());
//                }
//
//            });
//            return c;
//        }
    }

    @Test
    public void x() throws InterruptedException {

        System.out.println("Started...");
        Thread.sleep(10 * 60 * 1000);
    }
}
