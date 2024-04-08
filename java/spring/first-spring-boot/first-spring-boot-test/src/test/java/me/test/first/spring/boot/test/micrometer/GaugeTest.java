package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.actuator.ActuatorTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author dangqian.zll
 * @date 2024/3/28
 */
@Slf4j
@SpringBootTest(
        classes = {
                GaugeTest.Conf.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/actuator/ActuatorTest.yaml"
})
public class GaugeTest {

    Gauge gauge;


    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {

        @Path("/example")
        public class ExampleResource {
            private final LinkedList<Long> list = new LinkedList<>();

            private final MeterRegistry registry;

            public ExampleResource(MeterRegistry registry) {
                this.registry = registry;
                registry.gaugeCollectionSize("example.list.size", Tags.empty(), list);
            }

            @GET
            @Path("gauge/{number}")
            public Long checkListSize(@PathParam("number") long number) {
                if (number == 2 || number % 2 == 0) {
                    // add even numbers to the list
                    list.add(number);
                } else {
                    // remove items from the list for odd numbers
                    try {
                        number = list.removeFirst();
                    } catch (NoSuchElementException nse) {
                        number = 0;
                    }
                }
                return number;
            }
        }
    }


    /*
curl http://localhost:8080/example/gauge/1
curl http://localhost:8080/example/gauge/2
curl http://localhost:8080/example/gauge/4
curl http://localhost:8080/q/metrics
curl http://localhost:8080/example/gauge/6
curl http://localhost:8080/example/gauge/5
curl http://localhost:8080/example/gauge/7
curl http://localhost:8080/q/metrics
     */
}
