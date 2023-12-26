package io.github.btpka3.first.block.hound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class FirstBlockHoundApplication {

	public static void main(String[] args) {
		BlockHound.install();
		SpringApplication.run(FirstBlockHoundApplication.class, args);

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
