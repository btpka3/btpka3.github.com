package me.test.first.spring.boot.test.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

/**
 * 通过 OpenTelemetryAppender 可直接 将 logback 的 日志消息输出到 OpenTelemetry 对应的 exporter 里。
 *
 * @author dangqian.zll
 * @date 2024/4/7
 * @see io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender
 * @see io.opentelemetry.instrumentation.logback.appender.v1_0.internal.LoggingEventMapper#mapLoggingEvent(io.opentelemetry.api.logs.LogRecordBuilder, ch.qos.logback.classic.spi.ILoggingEvent, long)
 * @see io.opentelemetry.api.logs.LogRecordBuilder
 * @see io.opentelemetry.exporter.logging.LoggingMetricExporter
 * @see io.opentelemetry.exporter.logging.LoggingSpanExporter
 * @see io.opentelemetry.semconv.ResourceAttributes
 * @see io.opentelemetry.semconv.ClientAttributes
 */
public class LoggerApiTest {

    /*
   https://github.com/open-telemetry/semantic-conventions-java/blob/release/v1.23.1/src/main/java/io/opentelemetry/semconv/SemanticAttributes.java
   KEY                  : opentelemetry.semconv:1.23.1-alpha
   service.name         : ResourceAttributes.SERVICE_NAME           :
   service.version      : ResourceAttributes.SERVICE_VERSION        :
   service.instance.id  : ResourceAttributes.SERVICE_INSTANCE_ID    :
   host.name            : ResourceAttributes.HOST_NAME              :
   process.pid          : ResourceAttributes.PROCESS_PID            :
*/

    public void x() {
        Attributes attributes = Attributes.builder()
//                .put(ResourceAttributes.SERVICE_NAME, "dice-service")
//                .put(ResourceAttributes.SERVICE_VERSION, "0.1.0")
//                .put(ResourceAttributes.SERVICE_INSTANCE_ID, "dice-service-1")
//                .put(ResourceAttributes.HOST_NAME, System.getenv("HOSTNAME"))
//                .put(ResourceAttributes.PROCESS_PID, ProcessHandle.current().pid())
                .put("process.pid", ProcessHandle.current().pid())
                .build();
        Resource resource = Resource.getDefault()
                .merge(Resource.create(attributes));

        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
                .setResource(resource)
                .build();

        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder().build()).build())
                .setResource(resource)
                .build();
        SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
                .setResource(resource)
                .build();

        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setMeterProvider(sdkMeterProvider)
                .setLoggerProvider(sdkLoggerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();
    }
}
