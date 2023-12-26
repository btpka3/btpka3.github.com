package io.github.btpka3.first.block.hound;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootTest
class FirstBlockHoundApplicationTests {

    @Test
    void contextLoads() {

        BlockHound.install();

        Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();
        System.out.println("Done.");
    }

}
