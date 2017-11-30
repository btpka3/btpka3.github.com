package me.test.first.spring.cloud.sleuth.service;

import org.springframework.cloud.sleuth.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;

@Service
@SpanName("myServiceSpanName")
public class MyService implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "S";
    }
}
