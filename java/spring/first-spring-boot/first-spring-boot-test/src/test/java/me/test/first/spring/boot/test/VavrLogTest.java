package me.test.first.spring.boot.test;

import io.micrometer.core.instrument.Clock;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2020/11/20
 * @see <a href="https://www.vavr.io/">vavr</a>
 */
@Slf4j
public class VavrLogTest {


    @Test
    public void testLogWithRtAndException01() {
        long startTime = Clock.SYSTEM.monotonicTime();
        Try tryThis = Try.of(() -> sayHi("shouldThrowErr001"));

        tryThis.andFinally(() -> {
            long endTime = Clock.SYSTEM.monotonicTime();
            long cost = endTime - startTime;
            if (tryThis.isSuccess()) {
                System.out.println("handle success. result=" + tryThis.get() + ", rt=" + cost);
            } else {
                Throwable err = tryThis.getCause();
                System.err.println("failed to handle. rt=" + cost + ", exception = " + err);
            }
        });
    }

    @Test
    public void testLogWithRtAndResult01() {
        long startTime = System.currentTimeMillis();
        Try tryThis = Try.of(() -> sayHi("zhang3"));

        tryThis.andFinally(() -> {
            long endTime = System.currentTimeMillis();
            long cost = endTime - startTime;
            if (tryThis.isSuccess()) {
                System.out.println("handle success. result=" + tryThis.get() + ", rt=" + cost);
            } else {
                Throwable err = tryThis.getCause();
                System.err.println("failed to handle. rt=" + cost + ", exception = " + err);
            }
        });

    }

    protected String sayHi(String name) throws InterruptedException {
        if (name == null) {
            return "Hi, guest";
        }

        if (name.contains("Err")) {
            throw new IllegalArgumentException("Something is wrong");
        }

        Thread.sleep(101);

        return "Hi, " + name;
    }

}
