package io.github.btpka3.first.flink.api.table;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.row;

/**
 * @author dangqian.zll
 * @date 2024/7/8
 */
public class DemoTableTest {

    @SneakyThrows
    @Test
    public void showTableData() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

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
        String sql = "SELECT * FROM S";
        System.out.println("===== explainSql(sql):\n" + tEnv.explainSql(sql));

        Table table2 = tEnv.sqlQuery(sql);
        DataStream<Row> dataStream = tEnv.toAppendStream(table2, Row.class);

        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }
}
