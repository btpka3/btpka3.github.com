package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.LinkedList;

/**
 * @author dangqian.zll
 * @date 2024/3/28
 */
public class CounterTest extends BaseMetricsTest{

    Counter counter;

    public static class Conf{

        @Path("/example")
        public class ExampleResource {

            private final MeterRegistry registry;

            public ExampleResource(MeterRegistry registry) {
                this.registry = registry;
            }

            @GET
            @Path("prime/{number}")
            public String checkIfPrime(@PathParam("number") long number) {
                final String counterName = "example.prime.number";
                if (number < 1) {
                    registry.counter(counterName, "type", "not-natural")
                            .increment();
                    return "Only natural numbers can be prime numbers.";
                }
                if (number == 1) {
                    registry.counter(counterName, "type", "one")
                            .increment();
                    return number + " is not prime.";
                }
                if (number == 2 || number % 2 == 0) {
                    registry.counter(counterName, "type", "even")
                            .increment();
                    return number + " is not prime.";
                }
                if (timedTestPrimeNumber(number)) {
                    registry.counter(counterName, "type", "prime")
                            .increment();
                    return number + " is prime.";
                } else {
                    registry.counter(counterName, "type", "not-prime")
                            .increment();
                    return number + " is not prime.";
                }
            }
            protected boolean timedTestPrimeNumber(long number) {
                Timer.Sample sample = Timer.start(registry);
                boolean result = testPrimeNumber(number);
                sample.stop(registry.timer("example.prime.number.test", "prime", result + ""));
                return result;
            }

            protected boolean testPrimeNumber(long number) {
                for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
                    if (number % i == 0) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Test
    @SneakyThrows
    public void test() {
        Thread.sleep(60 * 60 * 1000);
    }

    /*
            curl -v http://localhost:8080/
            curl -v http://localhost:8080/actuator | jq
            curl -v http://localhost:8080/actuator/metrics | jq
            curl -v http://localhost:8080/actuator/metrics/executor.pool.core | jq


curl http://localhost:8080/example/gauge/1
curl http://localhost:8080/example/gauge/2
curl http://localhost:8080/example/gauge/4
curl http://localhost:8080/actuator/metrics
curl http://localhost:8080/actuator/metrics
curl http://localhost:8080/example/gauge/6
curl http://localhost:8080/example/gauge/5
curl http://localhost:8080/example/gauge/7
curl http://localhost:8080/q/metrics
     */
}
