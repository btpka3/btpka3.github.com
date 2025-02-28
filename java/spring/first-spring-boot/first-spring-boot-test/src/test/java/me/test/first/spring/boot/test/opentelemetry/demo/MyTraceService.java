package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dangqian.zll
 * @date 2024/4/8
 */
@Slf4j
@Component
public class MyTraceService {
    OpenTelemetry openTelemetry;
    Tracer tracer;
    MyMetricService myMetricService;
    MyLoggingService myLoggingService;

    public MyTraceService(
            OpenTelemetry openTelemetry,
            MyMetricService myMetricService,
            MyLoggingService myLoggingService
    ) {
        System.out.println("===================== MyTraceService created");
        this.openTelemetry = openTelemetry;
        this.tracer = openTelemetry.getTracer("myDemoScope", "1.0.0");
        this.myMetricService = myMetricService;
        this.myLoggingService = myLoggingService;
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void exec() {

        System.out.println("===================== MyTraceService#exec");
        Span span = tracer.spanBuilder("parent-aaa")
                .setSpanKind(SpanKind.CLIENT)
                //.addLink(???)
                .startSpan();

        span.setAttribute("g9App", "GET");
        span.setAttribute("bean", "GET");
        span.setAttribute("http.method", "GET");
        span.setAttribute("http.url", "/user/1.json");

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
            myMetricService.run();
            myLoggingService.run();
            System.out.println("b01-222");
        } catch (Throwable t) {
            span.recordException(t);
            log.error("b01 err", t);
        } finally {
            span.end();
        }
    }

    protected void b02() {
        Span span = tracer.spanBuilder("child-b02").startSpan();
        //Span span = Span.current()
        try (Scope scope = span.makeCurrent()) {
            System.out.println("b02-111");
            myMetricService.run();
            myLoggingService.run();
            System.out.println("b02-222");
        } catch (Throwable t) {
            span.recordException(t);
            log.error("b02 err", t);
        } finally {
            span.end();
        }
    }
}
