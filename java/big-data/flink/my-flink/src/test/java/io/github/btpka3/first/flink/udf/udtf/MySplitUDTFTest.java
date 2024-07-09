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
 * @date 2024/7/9
 */
public class MySplitUDTFTest {

    @SneakyThrows
    @Test
    public void programExec01() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.createFunction("MySplitUDTF", MySplitUDTF.class, true);


        Table table1 = tEnv.fromValues(
                ROW(
                        FIELD("a", STRING()),
                        FIELD("b", STRING()),
                        FIELD("c", STRING())
                ),
                row("a1", "b1", "aaa,bbb,ccc"),
                row("a2", "b2", "111,222,333"),
                row("a3", "b2", "xxx,yyy,zzz")
        );

        tEnv.createTemporaryView("S", table1);

        Table table2 = table1.joinLateral(call(MySplitUDTF.class, $("c")).as("s"))
                .select($("a"), $("b"), $("c"), $("s"));


        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : ");
        list.forEach(System.out::println);

    }

}
