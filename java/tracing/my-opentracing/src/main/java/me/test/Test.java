package me.test;

import io.opentracing.*;

public class Test {
    public static void main(String[] args) {
        Tracer tracer = null;


        ActiveSpan as1 =   tracer.activeSpan();
        //;

        //tracer.extract();

        try (ActiveSpan activeSpan = tracer.buildSpan("someWork").startActive()) {
            // Do things.
        } finally {
            //activeSpan.deactivate();
        }

        Span http = tracer.buildSpan("HandleHTTPRequest")
               // .asChildOf(rpcSpanContext)  // an explicit parent
                .withTag("user_agent", "xxx")
                .withTag("lucky_number", 42)
                .startManual();
    }
}
