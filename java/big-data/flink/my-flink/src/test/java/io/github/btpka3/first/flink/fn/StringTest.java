package io.github.btpka3.first.flink.fn;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class StringTest {

    @SneakyThrows
    @Test
    public void date001() {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        String sql = "SELECT 'a''b' as str";
        Table table = tEnv.sqlQuery(sql);
        DataStream<Row> dataStream = tEnv.toAppendStream(table, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
        assertEquals(1, list.size());
        Row row0 = list.get(0);
        assertEquals("a'b", row0.getField(0));
        assertEquals("a'b", row0.getField("str"));
    }


}
