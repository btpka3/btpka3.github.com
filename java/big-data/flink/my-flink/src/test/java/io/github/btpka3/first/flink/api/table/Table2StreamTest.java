package io.github.btpka3.first.flink.api.table;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class Table2StreamTest {

    /**
     * console 控制台 正则搜索: `\d `
     */
    @SneakyThrows
    @Test
    public void table2Stream() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        // stream -> table
        DataStream<String> dataStream = env.fromElements("Alice", "Bob", "John");

        Table inputTable = tEnv.fromDataStream(dataStream);

        tEnv.createTemporaryView("InputTable", inputTable);

        // table -> stream
        Table resultTable = tEnv.sqlQuery("SELECT UPPER(f0) FROM InputTable");
        DataStream<Row> resultStream = tEnv.toDataStream(resultTable);

        resultStream.print();
        env.execute();

        /* 示例输出：
            6> +I[BOB]
            7> +I[JOHN]
            5> +I[ALICE]
         */
    }
}
