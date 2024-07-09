package io.github.btpka3.first.flink.udf;

import io.github.btpka3.first.flink.udf.udtf.MyPersonUdtf;
import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class MyIsoTimeStrToTimestampUdfTest {

    @SneakyThrows
    @Test
    public void TO_TIMESTAMP02() {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        tEnv.createFunction("MyIsoTimeStrToTimestampUdf", MyIsoTimeStrToTimestampUdf.class, true);
        String sql = "SELECT MyIsoTimeStrToTimestampUdf('2024-01-02T03:04:05.789+08:00')";
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
}
