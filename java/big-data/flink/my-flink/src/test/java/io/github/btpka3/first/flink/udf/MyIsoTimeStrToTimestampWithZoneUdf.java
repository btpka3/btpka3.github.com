package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class MyIsoTimeStrToTimestampWithZoneUdf extends ScalarFunction {
    public ZonedDateTime eval(String str) {
        return ZonedDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}