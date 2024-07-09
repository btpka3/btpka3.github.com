package io.github.btpka3.first.flink.fn;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 * @see org.apache.flink.table.functions.BuiltInFunctionDefinitions#UNIX_TIMESTAMP
 * @see org.apache.flink.table.utils.DateTimeUtils#unixTimestamp(long)
 * @see org.apache.flink.table.planner.functions.sql.FlinkSqlOperatorTable#UNIX_TIMESTAMP
 */
public class TimestampTest {

    /**
     * 注意：只精确到秒
     */
    @SneakyThrows
    @Test
    public void UNIX_TIMESTAMP01() {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        String sql = "SELECT UNIX_TIMESTAMP('2024-01-02T03:04:05.789+08:00', 'yyyy-MM-dd''T''HH:mm:ss.SSSX')";
        Table table = tEnv.sqlQuery(sql);
        DataStream<Row> dataStream = tEnv.toAppendStream(table, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
        assertEquals(1, list.size());
        Row row0 = list.get(0);
        assertEquals(1704135845L, row0.getField(0));
    }

    /**
     * TO_TIMESTAMP 不支持有时区的字符串解析
     */

    @SneakyThrows
    @Test
    public void TO_TIMESTAMP02() {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        String sql = null;
        // 注意：使用下面这个带时区的时间字符串，返回值为null
        //sql = "SELECT TO_TIMESTAMP('2024-01-02T03:04:05.789+08:00', 'yyyy-MM-dd''T''HH:mm:ss.SSSX')";
        sql = "SELECT TO_TIMESTAMP('2024-01-02T03:04:05.789', 'yyyy-MM-dd''T''HH:mm:ss.SSS')";
        Table table = tEnv.sqlQuery(sql);
        DataStream<Row> dataStream = tEnv.toAppendStream(table, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
        assertEquals(1, list.size());
        Row row0 = list.get(0);
        LocalDateTime time = (LocalDateTime) row0.getField(0);
        assertNotNull(time);
        assertEquals(1704135845789L, time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }


    public String getTimeStr(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public LocalDateTime fromTimeStr(String str) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zonedDateTime.toLocalDateTime();
    }

}
