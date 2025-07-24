package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.baggage.Baggage;

/**
 *
 * http 请求头: `traceparent`, `tracestate`, `baggage`
 *
 * @author dangqian.zll
 * @date 2025/6/20
 * @see <a href="https://opentelemetry.io/docs/specs/otel/context/api-propagators/#propagators-distribution">Propagators Distribution</a>
 * @see <a href="https://www.w3.org/TR/trace-context/">W3C TraceContext</a>
 * @see <a href="https://www.w3.org/TR/baggage/">W3C Baggage Specification</a>
 */
public class BaggageTest {

    public void x() {
        Baggage baggage = Baggage.current();
    }
}
