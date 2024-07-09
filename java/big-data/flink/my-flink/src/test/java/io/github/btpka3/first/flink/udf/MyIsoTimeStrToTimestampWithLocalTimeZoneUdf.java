package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class MyIsoTimeStrToTimestampWithLocalTimeZoneUdf extends ScalarFunction {
    public Instant eval(String str) {
        ZonedDateTime zdt = ZonedDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zdt.toInstant();
    }
}