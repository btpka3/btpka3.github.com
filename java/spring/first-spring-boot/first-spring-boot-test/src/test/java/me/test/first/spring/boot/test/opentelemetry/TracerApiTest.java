package me.test.first.spring.boot.test.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapSetter;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.SemanticAttributes;
import lombok.SneakyThrows;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author dangqian.zll
 * @date 2024/3/20
 * @see <a href="https://opentelemetry.io/docs/languages/java/instrumentation/#metrics">opentelemetry : Java/Instrumentation</a>
 * @see <a href="https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/logback/logback-appender-1.0/library">Appender Instrumentation for Logback version 1.0 and higher</a>
 * @see <a href="https://help.aliyun.com/zh/opentelemetry/user-guide/use-opentelemetry-to-submit-the-trace-data-of-java-applications">通过OpenTelemetry上报Java应用数据</a>
 */
public class TracerApiTest {
    OpenTelemetry openTelemetry = null;
    Tracer tracer;

    public void x() {



        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(LoggingSpanExporter.create()).build())
                //.setResource(resource)
                .setSampler(Sampler.traceIdRatioBased(0.5))
                .build();


        Tracer tracer = openTelemetry.getTracer("myDubbo", "1.0.0");

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


    @SneakyThrows
    public void injectHttp() {
        TextMapSetter<HttpURLConnection> setter = new TextMapSetter<HttpURLConnection>() {
            @Override
            public void set(HttpURLConnection carrier, String key, String value) {
                // Insert the context as Header
                carrier.setRequestProperty(key, value);
            }
        };
        URL url = new URL("http://127.0.0.1:8080/resource");
        Span outGoing = tracer.spanBuilder("/resource").setSpanKind(SpanKind.CLIENT).startSpan();
        try (Scope scope = outGoing.makeCurrent()) {
            // Use the Semantic Conventions.
            // (Note that to set these, Span does not *need* to be the current instance in Context or Scope.)
            outGoing.setAttribute(SemanticAttributes.HTTP_METHOD, "GET");
            outGoing.setAttribute(SemanticAttributes.HTTP_URL, url.toString());
            HttpURLConnection transportLayer = (HttpURLConnection) url.openConnection();
            // Inject the request with the *current*  Context, which contains our current Span.
            openTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), transportLayer, setter);
            // Make outgoing call
        } finally {
            outGoing.end();
        }
    }


    public void meter() {
        Meter meter = openTelemetry.meterBuilder("instrumentation-library-name")
                .setInstrumentationVersion("1.0.0")
                .build();
        // Build counter e.g. LongCounter
        LongCounter counter = meter
                .counterBuilder("processed_jobs")
                .setDescription("Processed jobs")
                .setUnit("1")
                .build();

        // It is recommended that the API user keep a reference to Attributes they will record against
        Attributes attributes = Attributes.of(AttributeKey.stringKey("Key"), "SomeWork");

        // Record data
        counter.add(123, attributes);
    }

    public void log() {
        OpenTelemetryAppender.install(openTelemetry);
    }

    public void loggingExporter() {
        LoggingSpanExporter exporter = LoggingSpanExporter.create();

        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
                .build();

        OtlpGrpcLogRecordExporter ddd;
        OtlpJsonLoggingSpanExporter e;
    }
}
