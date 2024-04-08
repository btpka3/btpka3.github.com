package me.test.first.spring.boot.test.opentelemetry.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author dangqian.zll
 * @date 2024/4/8
 */
@Component
public class MyLoggingService implements Runnable {
    @Override
    public void run() {
        Logger logger = LoggerFactory.getLogger("my.OpenTelemetryLogger");
        Throwable err = new IllegalArgumentException("demo log err");
        logger.error("check_OpenTelemetry_log_export_result", err);
    }
}
