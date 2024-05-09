package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.ReadableSpan;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/5/7
 * @see io.opentelemetry.api.incubator.trace.ExtendedTracer
 * @see io.opentelemetry.api.incubator.trace.ExtendedSpanBuilder#startAndCall(io.opentelemetry.api.incubator.trace.SpanCallable)
 */
public class SpanTest {

    protected static String getSpanStrValue(Span span, String key) {

        if (span instanceof ReadableSpan) {
            return ((ReadableSpan) span).getAttribute(AttributeKey.stringKey(key));
        }
        return null;
    }

    protected static SpanData getSpanData(Span span) {

        if (span instanceof ReadableSpan) {
            return ((ReadableSpan) span).toSpanData();
        }
        return null;
    }


    protected OpenTelemetry getOpenTelemetry() {
        Resource resource = Resource.getDefault().toBuilder()
                .put("service.name", "btpka3-server")
                .build();

        SdkTracerProvider sdkTracerProvider = null;
        {
            SpanExporter spanExporter = SpanExporter.composite(
                    OtlpJsonLoggingSpanExporter.create()
            );
            SpanProcessor spanProcessor = BatchSpanProcessor.builder(spanExporter)
                    .build();

            sdkTracerProvider = SdkTracerProvider.builder()
                    .addSpanProcessor(spanProcessor)
                    .addSpanProcessor(new MySpanProcessor())
                    .setResource(resource)
                    .build();
        }

        ContextPropagators contextPropagators = ContextPropagators.create(TextMapPropagator.composite(
                W3CTraceContextPropagator.getInstance(),
                W3CBaggagePropagator.getInstance()
        ));

        return OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(contextPropagators)
                .buildAndRegisterGlobal();
    }


    @Test
    public void test01() {
        OpenTelemetry openTelemetry = getOpenTelemetry();
        Tracer tracer = openTelemetry.getTracer("xxx");

        System.out.println("test01.0. span.a=" + getSpanStrValue(Span.current(), "a"));
        System.out.println("test01.0. span.b=" + getSpanStrValue(Span.current(), "b"));
        System.out.println("test01.0. span.c=" + getSpanStrValue(Span.current(), "c"));


        Span span = tracer.spanBuilder("parent-aaa")
                .setSpanKind(SpanKind.CLIENT)
                //.addLink(???)
                .startSpan();

        System.out.println("span.class=" + span.getClass());
        SpanData spanData = getSpanData(span);
        System.out.println("test01. span.traceId=" + spanData.getTraceId());
        System.out.println("test01. span.spanId=" + spanData.getSpanId());
        System.out.println("test01. span.parentSpanId=" + spanData.getParentSpanId());

        System.out.println("test01.1. span.a=" + getSpanStrValue(span, "a"));
        System.out.println("test01.1. span.b=" + getSpanStrValue(span, "b"));
        System.out.println("test01.1. span.c=" + getSpanStrValue(span, "c"));

        span.setAttribute("a", "a01");
        span.setAttribute("b", "b01");

        System.out.println("test01.2. span.a=" + getSpanStrValue(span, "a"));
        System.out.println("test01.2. span.b=" + getSpanStrValue(span, "b"));
        System.out.println("test01.2. span.c=" + getSpanStrValue(span, "c"));


        try (Scope scope = span.makeCurrent()) {
            b01(tracer);

        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "xxx err");
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }
        System.out.println("test01.3. span.a=" + getSpanStrValue(span, "a"));
        System.out.println("test01.3. span.b=" + getSpanStrValue(span, "b"));
        System.out.println("test01.3. span.c=" + getSpanStrValue(span, "c"));


    }


    public void b01(Tracer tracer) {
        // 注意：如果手动传递 parentSpanData.getAttributes(), 则后面的 a==null
        SpanData parentSpanData = getSpanData(Span.current());
        Span span = tracer.spanBuilder("child-b01")
                //.setAllAttributes(parentSpanData.getAttributes())
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            SpanData spanData = getSpanData(span);
            System.out.println("b01. span.traceId=" + spanData.getTraceId());
            System.out.println("b01. span.spanId=" + spanData.getSpanId());
            System.out.println("b01. span.parentSpanId=" + spanData.getParentSpanId());

            System.out.println("b01.1. span.a=" + getSpanStrValue(span, "a"));
            System.out.println("b01.1. span.b=" + getSpanStrValue(span, "b"));
            System.out.println("b01.1. span.c=" + getSpanStrValue(span, "c"));

            span.setAttribute("b", "a02");
            span.setAttribute("c", "c02");

            System.out.println("b01.2. span.a=" + getSpanStrValue(span, "a"));
            System.out.println("b01.2. span.b=" + getSpanStrValue(span, "b"));
            System.out.println("b01.2. span.c=" + getSpanStrValue(span, "c"));

        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "xxx err");
            span.recordException(t);
            throw t;
        } finally {
            span.end(); // Cannot set a span after this call
        }


    }

    public static class MySpanProcessor implements SpanProcessor {

        @Override
        public void onStart(Context parentContext, ReadWriteSpan span) {
            Span parentSpan = Span.fromContext(parentContext);
            SpanData parentSpanData = getSpanData(parentSpan);
            String parentSpanId = parentSpanData == null ? null : parentSpanData.getSpanId();
            System.out.println("MySpanProcessor : parentSpan.spanId=" + parentSpanId + ", a=" + getSpanStrValue(parentSpan, "a"));
            System.out.println("MySpanProcessor : span.spanId=" + span.toSpanData().getSpanId() + ", a=" + getSpanStrValue(span, "a"));
        }

        @Override
        public boolean isStartRequired() {
            return true;
        }

        @Override
        public void onEnd(ReadableSpan span) {

        }

        @Override
        public boolean isEndRequired() {
            return false;
        }
    }
}
