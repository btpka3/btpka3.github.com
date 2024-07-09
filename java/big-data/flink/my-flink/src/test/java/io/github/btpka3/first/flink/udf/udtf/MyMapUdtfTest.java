package io.github.btpka3.first.flink.udf.udtf;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.*;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyMapUdtfTest {
    @SneakyThrows
    @Test
    public void programExec() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyMapUdtf", MyMapUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", STRING())
                        ),
                        row("zhang3", "33"),
                        row("li4", "44"),
                        row("wang5", "55")
                )
                .joinLateral(call("MyMapUdtf",
                        map("name", $("name"), "age", $("age"))
                ).as("myMap"))
                .select($("name"), $("age"), $("myMap"));
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }
}
