package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.ReadableSpan;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.data.SpanData;

public class DemoSpanProcessor implements SpanProcessor {
    @Override
    public void onStart(Context parentContext, ReadWriteSpan span) {
        Span parentSpan = Span.fromContext(parentContext);
        if (parentSpan instanceof ReadableSpan) {
            SpanData parentSpanData = ((ReadableSpan) span).toSpanData();
            parentSpanData.getAttributes().forEach((key, value) -> {
                if (span.getAttribute(key) == null) {
                    span.setAttribute((AttributeKey<Object>) key, value);
                }
            });
        }
    }
    @Override
    public boolean isStartRequired() {return true;}
    @Override
    public void onEnd(ReadableSpan span) {}
    @Override
    public boolean isEndRequired() {return false;}
}
