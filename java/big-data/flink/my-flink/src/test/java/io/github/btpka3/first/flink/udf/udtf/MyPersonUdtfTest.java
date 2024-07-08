package io.github.btpka3.first.flink.udf.udtf;

import io.github.btpka3.first.flink.udf.Person;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.*;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyPersonUdtfTest {

    @SneakyThrows
    @Test
    public void sssa() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyPersonUdtf", MyPersonUdtf.class, true);
        tEnv.createFunction("MyMapUdtf", MyMapUdtf.class, true);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

        Table table1 = tEnv.fromValues(
                ROW(
                        FIELD("name", STRING()),
                        FIELD("age", INT())
                ),
                row("zhang3", 33),
                row("li4", 44),
                row("wang5", 55)
        );
        tEnv.createTemporaryView("S", table1);
        System.out.println("===== S=" + table1);
        System.out.println("===== S.getResolvedSchema()=" + table1.getResolvedSchema());

        String sql = IOUtils.toString(
                MyPersonUdtfTest.class.getResourceAsStream("MyPersonUdtfTest.01.sql"),
                StandardCharsets.UTF_8);
        System.out.println("===== sql:\n" + sql);

        Table table2 = tEnv.sqlQuery(sql);
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);

    }


    @SneakyThrows
    @Test
    public void programExec() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyPersonUdtf", MyPersonUdtf.class, true);
        tEnv.createFunction("MyMapUdtf", MyMapUdtf.class, true);
        tEnv.createFunction("MyRowUdtf", MyRowUdtf.class, true);

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

    @SneakyThrows
    @Test
    public void programExec01() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MyPersonUdtf", MyPersonUdtf.class, true);

        Table table2 = tEnv.fromValues(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", STRING())
                        ),
                        row(Person.builder().name("zhang3").age(33).build()),
                        row(Person.builder().name("li4").age(44).build()),
                        row(Person.builder().name("wang5").age(55).build())
                )
                .joinLateral(call("MyPersonUdtf",
                        map("name", $("name"), "age", $("age"))
                ).as("myPerson"))
                .select($("name"), $("age"), $("myPerson"));
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }
}
