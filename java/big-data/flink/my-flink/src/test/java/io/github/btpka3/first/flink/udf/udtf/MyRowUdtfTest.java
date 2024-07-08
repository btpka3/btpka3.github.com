package io.github.btpka3.first.flink.udf.udtf;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.JsonOnNull;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.*;

/**
 * @author dangqian.zll
 * @date 2024/7/8
 */
public class MyRowUdtfTest {

    /**
     * UDTF 的 入参是Map类型.  运行报错。Map 的value 必须是同一种类型，这里 是 SRING/INT
     */
    @SneakyThrows
    @Test
    public void programExecMap() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", INT())
                        ),
                        row("zhang3", 33),
                        row("li4", 44),
                        row("wang5", 55)
                )
                .joinLateral(call("MyRowUdtf",
                        map("name", $("name"), "age", $("age"))
                ).as("myName", "myAge"))
                .select(
                        $("name"),
                        $("age"),
                        $("myName"),
                        $("myAge")
                );
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }

    /**
     * UDTF 的 入参是ROW类型
     */
    @SneakyThrows
    @Test
    public void programExecRow() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", INT())
                        ),
                        row("zhang3", 33),
                        row("li4", 44),
                        row("wang5", 55)
                )
                .joinLateral(
                        call("MyRowUdtf",
                                row($("name"), $("age"))
                        )
                                .as("myName", "myAge")
                )
                .select(
                        $("name"),
                        $("age"),
                        $("myName"),
                        $("myAge")
                );
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }

    /**
     * UDTF 的 入参是 JSON 字符串类型
     */
    @SneakyThrows
    @Test
    public void programExecJson() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("content", STRING())
                        ),
                        row("{\"name\":\"zhang3\",\"age\":33}"),
                        row("{\"name\":\"li4\",\"age\":44}"),
                        row("{\"name\":\"wang5\",\"age\":55}")
                )
                .joinLateral(
                        call("MyRowUdtf", $("content"))
                                .as("myName", "myAge"))
                .select(
                        $("content"),
                        $("myName"),
                        $("myAge")
                );
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }

    /**
     * UDTF 的 入参是 JSON 字符串类型
     */
    @SneakyThrows
    @Test
    public void programExecJson2() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", INT())
                        ),
                        row("zhang3", 33),
                        row("li4", 44),
                        row("wang5", 55)
                )
                .joinLateral(
                        call("MyRowUdtf", jsonObject(JsonOnNull.NULL, "name", $("name"), "age", $("age")))
                                .as("myName", "myAge"))
                .select(
                        $("name"),
                        $("age"),
                        $("myName"),
                        $("myAge")
                );
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }
}
