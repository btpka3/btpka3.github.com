package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class MyIsoTimeStrToTimestampUdf extends ScalarFunction {
    public LocalDateTime eval(String str) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zonedDateTime.toLocalDateTime();
    }
}