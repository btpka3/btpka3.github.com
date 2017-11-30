package me.test.first.spring.cloud.sleuth.conf;

import org.springframework.cloud.sleuth.*;
import org.springframework.cloud.sleuth.sampler.*;
import org.springframework.context.annotation.*;

@Configuration
public class SleuthConf {

    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
}
