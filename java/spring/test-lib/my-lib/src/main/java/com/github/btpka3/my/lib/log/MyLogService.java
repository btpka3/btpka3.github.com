package com.github.btpka3.my.lib.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import static net.logstash.logback.marker.Markers.append;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
@Slf4j
public class MyLogService {

    public static void testLog01() {

        MDC.put("traceId", "traceId001");

        log.info("aaa-{}", System.currentTimeMillis());
        log.info(
                append("name", "zhang3")
                        .and(append("age", "35"))
                        .and(append("height", "182")),
                "json-test={}",
                System.currentTimeMillis()
        );
        System.out.println("---------------done : " + System.currentTimeMillis());

    }
}
