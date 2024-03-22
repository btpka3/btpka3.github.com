package me.test.first.spring.boot.test.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.semconv.ResourceAttributes;
import io.opentelemetry.semconv.SemanticAttributes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dangqian.zll
 * @date 2024/3/20
 */
@Slf4j
@SpringBootTest
@ContextConfiguration
public class TracerDemoTest {


    @Configuration
    public static class Conf {
        @Bean
        public OpenTelemetry openTelemetry() {
            Resource resource = Resource.getDefault().toBuilder()
                    .put(ResourceAttributes.SERVICE_NAME, "dice-server")
                    .put(ResourceAttributes.SERVICE_VERSION, "0.1.0")
                    .build();

            SpanExporter exporter = OtlpJsonLoggingSpanExporter.create();
            //SpanExporter exporter = LoggingSpanExporter.create();
            SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                    .addSpanProcessor(SimpleSpanProcessor.create(exporter))
                    .setResource(resource)
                    .build();

            SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                    .registerMetricReader(PeriodicMetricReader.builder(LoggingMetricExporter.create()).build())
                    .setResource(resource)
                    .build();
// .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())

            SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
                    .addLogRecordProcessor(BatchLogRecordProcessor.builder(SystemOutLogRecordExporter.create()).build())
                    .setResource(resource)
                    .build();

            OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                    .setTracerProvider(sdkTracerProvider)
                    .setMeterProvider(sdkMeterProvider)
                    .setLoggerProvider(sdkLoggerProvider)
                    .setPropagators(ContextPropagators.create(TextMapPropagator.composite(W3CTraceContextPropagator.getInstance(), W3CBaggagePropagator.getInstance())))
                    .buildAndRegisterGlobal();

            return openTelemetry;
        }

        @Bean
        Tracer demoTracer(OpenTelemetry openTelemetry) {
            return openTelemetry.getTracer("myScope", "1.0.0");
        }
    }


    @Autowired
    Tracer tracer;

    @Test
    public void test() throws InterruptedException {
        aaa();
        Thread.sleep(60 * 60 * 1000);
        System.out.println("Done.");
    }

//    static {
//        SLF4JBridgeHandler.removeHandlersForRootLogger();
//        SLF4JBridgeHandler.install();
//    }

    @SneakyThrows
    @Test
    public void testJul() {
        System.out.println("start.");
        {
            Logger logger = Logger.getLogger(TracerDemoTest.class.getName());
            logger.warning("testJul111");
            logger.log(Level.INFO, "testJul222");
        }
        {
            log.info("slf4j_logger_111");
            log.error("slf4j_logger_222");
        }
        System.out.println("done.");
        Thread.sleep(5 * 1000);
    }

    protected void aaa() {

        Span span = tracer.spanBuilder("parent-aaa")
                .setSpanKind(SpanKind.CLIENT)
                //.addLink(???)
                .startSpan();

        span.setAttribute("http.method", "GET");
        span.setAttribute(SemanticAttributes.HTTP_URL, "/user/1.json");

        span.addEvent("init");

        try (Scope scope = span.makeCurrent()) {
            System.out.println("aaa-111");

            // 演示同步执行
            b01();

            // 演示如何切换线程上下文
            Context context = Context.current();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try (Scope scope = context.makeCurrent()) {
                        b02();
                    }
                }
            });
            thread.start();
            System.out.println("aaa-222");
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "xxx err");
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }
    }

    protected void b01() {
        Span span = tracer.spanBuilder("child-b01").startSpan();
        //Span span = Span.current()
        try (Scope scope = span.makeCurrent()) {
            System.out.println("b01-111");
            System.out.println("b01-222");
        } catch (Throwable t) {
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }
    }

    protected void b02() {
        Span span = tracer.spanBuilder("child-b02").startSpan();
        //Span span = Span.current()
        try (Scope scope = span.makeCurrent()) {
            System.out.println("b02-111");
        } catch (Throwable t) {
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }
    }
}
