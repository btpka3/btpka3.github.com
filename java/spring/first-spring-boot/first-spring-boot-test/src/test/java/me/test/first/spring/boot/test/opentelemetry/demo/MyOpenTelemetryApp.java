package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingLogRecordExporter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

/**
 * @author dangqian.zll
 * @date 2024/4/8
 */
@Slf4j
@SpringBootTest(
        classes = {
                MyOpenTelemetryApp.Conf.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/opentelemetry/demo/MyOpenTelemetryApp.yaml"
})
public class MyOpenTelemetryApp {

    @EnableScheduling
    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    @Configuration
    public static class Conf {

        public static void main(String[] args) {
            SpringApplication.run(Conf.class, args);
        }

        @Bean
        public OpenTelemetry openTelemetry(
//                @Value("${my.opentelemetry.endpoint}") String endpoint,
//                @Value("${my.opentelemetry.token}") String token

        ) {
            Resource resource = Resource.getDefault().toBuilder()
                    .put("service.name", "btpka3-server")
                    .put("service.version", "0.1.0")
                    .build();

            SdkTracerProvider sdkTracerProvider = null;
            {
                SpanExporter spanExporter = SpanExporter.composite(
                        // LoggingSpanExporter.create()
                        // SimpleSpanProcessor.create(exporter)

                        // 写入到文件
                        // 以 loggerName="io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter" 按照当前 slf4j+logback 配置输出
                        // 输出示例参考： OtlpJsonLoggingSpanExporter.demo.json
                        OtlpJsonLoggingSpanExporter.create()

//                        // 上报到阿里云 OpenTelemetry
//                        OtlpGrpcSpanExporter.builder()
//                                .setEndpoint(endpoint)
//                                .addHeader("Authentication", token)
//                                .build()
                );
                SpanProcessor spanProcessor = BatchSpanProcessor.builder(spanExporter)
                        .build();

                sdkTracerProvider = SdkTracerProvider.builder()
                        .addSpanProcessor(spanProcessor)
                        .setResource(resource)
                        .build();
            }

            SdkMeterProvider sdkMeterProvider = null;
            {
                // 以 loggerName="io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter" 按照当前 slf4j+logback 配置输出
                // 输出示例参考： OtlpJsonLoggingMetricExporter.demo.json
                MetricExporter metricExporter = OtlpJsonLoggingMetricExporter.create();
                //MetricExporter metricExporter = LoggingMetricExporter.create();
                //MetricExporter metricExporter  = OtlpGrpcMetricExporter.builder()
                //        .build();
                sdkMeterProvider = SdkMeterProvider.builder()
                        .registerMetricReader(PeriodicMetricReader.builder(metricExporter)
                                .setInterval(Duration.ofSeconds(1))
                                .build())
                        .setResource(resource)
                        .build();
            }

            SdkLoggerProvider sdkLoggerProvider = null;

            {
                LogRecordExporter logRecordExporter = LogRecordExporter.composite(
                        // 以 loggerName="io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingLogRecordExporter" 按照当前 slf4j+logback 配置输出
                        // 输出示例参考： OtlpJsonLoggingLogRecordExporter.demo.json
                        OtlpJsonLoggingLogRecordExporter.create(),

                        // 输出示例参考: SystemOutLogRecordExporter.demo.txt
                        SystemOutLogRecordExporter.create()
                );

                sdkLoggerProvider = SdkLoggerProvider.builder()
                        .addLogRecordProcessor(BatchLogRecordProcessor.builder(logRecordExporter).build())
                        .setResource(resource)
                        .build();
            }

            ContextPropagators contextPropagators = ContextPropagators.create(TextMapPropagator.composite(
                    W3CTraceContextPropagator.getInstance(),
                    W3CBaggagePropagator.getInstance()
            ));

            OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                    .setTracerProvider(sdkTracerProvider)
                    .setMeterProvider(sdkMeterProvider)
                    .setLoggerProvider(sdkLoggerProvider)
                    .setPropagators(contextPropagators)
                    .buildAndRegisterGlobal();

            return openTelemetry;
        }
    }


    @Test
    @SneakyThrows
    public void test() {
        Thread.sleep(60 * 60 * 1000);

        /*

         */
    }

}
