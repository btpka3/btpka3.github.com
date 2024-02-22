package io.github.btpka3.first.flink;

import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;

import static org.apache.flink.table.api.DataTypes.*;

/**
 * @author dangqian.zll
 * @date 2024/2/21
 */
public class TableApiTest {

    public void x() {
        // Create a TableEnvironment for batch or streaming execution.
        // See the "Create a TableEnvironment" section for details.
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        // Create a source table
        tableEnv.createTemporaryTable("SourceTable", TableDescriptor.forConnector("datagen")
                .schema(Schema.newBuilder()
                        .column("f0", DataTypes.STRING())
                        .build())
                .option(DataGenConnectorOptions.ROWS_PER_SECOND, 100L)
                .build());

        // Create a sink table (using SQL DDL)
        tableEnv.executeSql("CREATE TEMPORARY TABLE SinkTable WITH ('connector' = 'blackhole') LIKE SourceTable (EXCLUDING OPTIONS) ");

        // Create a Table object from a Table API query
        Table table1 = tableEnv.from("SourceTable");

        // Create a Table object from a SQL query
        Table table2 = tableEnv.sqlQuery("SELECT * FROM SourceTable");

        // Emit a Table API result Table to a TableSink, same for SQL result
        TableResult tableResult = table1.insertInto("SinkTable").execute();

        // =====
        // Using table descriptors
        final TableDescriptor sourceDescriptor = TableDescriptor.forConnector("datagen")
                .schema(Schema.newBuilder()
                        .column("f0", DataTypes.STRING())
                        .build())
                .option(DataGenConnectorOptions.ROWS_PER_SECOND, 100L)
                .build();

        tableEnv.createTable("SourceTableA", sourceDescriptor);
        tableEnv.createTemporaryTable("SourceTableB", sourceDescriptor);

        // Using SQL DDL
        tableEnv.executeSql("CREATE [TEMPORARY] TABLE MyTable (...) WITH (...)");

    }

    public void dataStream2Table() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // create a DataStream
        DataStream<String> dataStream = env.fromElements("Alice", "Bob", "John");

        // interpret the insert-only DataStream as a Table
        Table inputTable = tableEnv.fromDataStream(dataStream);

        // register the Table object as a view and query it
        tableEnv.createTemporaryView("InputTable", inputTable);
        Table resultTable = tableEnv.sqlQuery("SELECT UPPER(f0) FROM InputTable");

        // interpret the insert-only Table as a DataStream again
        DataStream<Row> resultStream = tableEnv.toDataStream(resultTable);

        // add a printing sink and execute in DataStream API
        resultStream.print();
        env.execute();

    }

    /**
     * @see org.apache.flink.table.types.DataType
     */
    public void dataType() {
        DataType strArr = DataTypes.ARRAY(DataTypes.STRING());
        DataType t0 = INTERVAL(DAY(), SECOND(3));

        // tell the runtime to not produce or consume java.time.LocalDateTime instances
        // but java.sql.Timestamp
        DataType t1 = DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class);

        // tell the runtime to not produce or consume boxed integer arrays
        // but primitive int arrays
        DataType t2 = DataTypes.ARRAY(DataTypes.INT().notNull()).bridgedTo(int[].class);
    }
}
