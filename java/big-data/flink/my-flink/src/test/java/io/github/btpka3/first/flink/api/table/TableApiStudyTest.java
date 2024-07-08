package io.github.btpka3.first.flink.api.table;

import io.github.btpka3.first.flink.udf.Person;
import lombok.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.configuration.CleanupOptions;
import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.catalog.CatalogDatabase;
import org.apache.flink.table.catalog.CatalogDescriptor;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import static org.apache.flink.table.api.DataTypes.FIELD;
import static org.apache.flink.table.api.DataTypes.ROW;
import static org.apache.flink.table.api.Expressions.*;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
public class TableApiStudyTest {


    public void tEnv() {
        {
            EnvironmentSettings settings = EnvironmentSettings
                    .newInstance()
                    .inStreamingMode()
                    //.inBatchMode()
                    .build();
            TableEnvironment tEnv = TableEnvironment.create(settings);
        }
        // 从 Stream 创建一个 TableEnvironment
        {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        }
    }


    @Test
    public void ttt() {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        Table table = tEnv.fromValues(
                row(1, "ABC"),
                row(2L, "ABCDE")
        );

        System.out.println(table);
    }


    public void test01() {
        EnvironmentSettings settings = null;
        // Create a TableEnvironment for batch or streaming execution.
        // See the "Create a TableEnvironment" section for details.
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
    }


    public void catalog() {
        TableEnvironment tableEnv = null;
        CatalogDescriptor descriptor = null;
        if (tableEnv.getCatalog("catalog1").isPresent()) {
            tableEnv.createCatalog("catalog1", descriptor);
        }
        if (!"catalog1".equals(tableEnv.getCurrentCatalog())) {
            tableEnv.useCatalog("catalog1");
        }
    }

    public void database() throws DatabaseAlreadyExistException, DatabaseNotExistException {
        TableEnvironment tableEnv = null;
        CatalogDescriptor descriptor = null;
        Catalog catalog = tableEnv.getCatalog("catalog1").get();
        if (!catalog.databaseExists("database1")) {
            CatalogDatabase database = null;
            catalog.createDatabase("database1", database, true);
        }
        if (!"database1".equals(tableEnv.getCurrentDatabase())) {
            tableEnv.useDatabase("database1");
        }
        CatalogDatabase database1 = catalog.getDatabase("database1");
    }

    @SneakyThrows
    public void table() {
        TableEnvironment tableEnv = null;

        String[] tableNames = tableEnv.listTables();
        if (!ArrayUtils.contains(tableNames, "myTable")) {
            tableEnv.createTable("", TableDescriptor.forConnector("datagen")
                    .schema(Schema.newBuilder()
                            .column("f0", DataTypes.STRING())
                            .build()
                    )
                    .option(CleanupOptions.CLEANUP_STRATEGY_FIXED_DELAY_ATTEMPTS, Integer.MAX_VALUE)
                    .option("fields.f0.kind", "random")
                    .build()
            );
        }
        Table table = tableEnv.from("myTable");
        table.select(map("key1", 1, "key2", 2, "key3", 3));

        System.out.println(table.explain());
    }

    @SneakyThrows
    public void listTemporaryTables() {
        TableEnvironment tableEnv = null;
        String[] tableNames = tableEnv.listTemporaryTables();
        if (!ArrayUtils.contains(tableNames, "myTable")) {
            TableDescriptor descriptor = null;
            tableEnv.createTemporaryTable("xxxTable", descriptor);
        }
        Table table = tableEnv.from("myTable");
    }


    public void execSql() {
        TableEnvironment tableEnv = null;

        // 更新系
        TableResult tableResult = tableEnv.executeSql("CREATE TABLE my_table(...) WITH (...)");

        // 查询系
        Table table = tableEnv.sqlQuery("SELECT 'aaa'");
        ResolvedSchema resolvedSchema = table.getResolvedSchema();
        resolvedSchema.getColumn("f0").get().getDataType();

        table
                .filter($("name").isEqual("Fred"))
                .groupBy($("cID"), $("cName"))
                .select(
                        $("cID"),
                        $("cName"),
                        $("revenue").sum().as("revSum")
                );
        ;

        //
        tableEnv.fromValues(
                row(1, "ABC"),
                row(2L, "ABCDE")
        );
        tableEnv.fromValues(
                lit(1).plus(2),
                lit(2L), lit(3)
        );
        tableEnv.fromValues(
                call(new RowFunction()),
                call(new RowFunction())
        );

    }

    public static class RowFunction extends ScalarFunction {
        @DataTypeHint("ROW<f0 BIGINT, f1 VARCHAR(5)>")
        public Row eval() {
            return null;
        }
    }

    public void sink() {

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        final Schema schema = Schema.newBuilder()
                .column("a", DataTypes.INT())
                .column("b", DataTypes.STRING())
                .column("c", DataTypes.BIGINT())
                .build();

        tEnv.createTemporaryTable("CsvSinkTable", TableDescriptor.forConnector("filesystem")
                .schema(schema)
                .option("path", "/path/to/file")
                .format(FormatDescriptor.forFormat("csv")
                        .option("field-delimiter", "|")
                        .build())
                .build());

        Table result = null;

        // Prepare the insert into pipeline
        TablePipeline pipeline = result.insertInto("CsvSinkTable");
        pipeline.printExplain();

        // emit the result Table to the registered TableSink
        pipeline.execute();
    }


    public void xxx() {
        StreamTableEnvironment tableEnv = null;

        Table table = tableEnv.fromValues(
                ROW(
                        FIELD("name", DataTypes.STRING()),
                        FIELD("age", DataTypes.INT())
                ),
                row("john", 35),
                row("sarah", 32)
        );

        // Convert the Table into an append DataStream of Row by specifying the class
        DataStream<Row> dsRow = tableEnv.toAppendStream(table, Row.class);

        // Convert the Table into an append DataStream of Tuple2<String, Integer> with TypeInformation
        TupleTypeInfo<Tuple2<String, Integer>> tupleType = new TupleTypeInfo<>(Types.STRING(), Types.INT());
        DataStream<Tuple2<String, Integer>> dsTuple = tableEnv.toAppendStream(table, tupleType);

        // Convert the Table into a retract DataStream of Row.
        // A retract stream of type X is a DataStream<Tuple2<Boolean, X>>.
        // The boolean field indicates the type of the change.
        // True is INSERT, false is DELETE.
        DataStream<Tuple2<Boolean, Row>> retractStream = tableEnv.toRetractStream(table, Row.class);
    }

    public void streamType2tableSchema() {
        StreamTableEnvironment tableEnv = null;

        DataStream<Tuple2<Long, Integer>> stream = null;

        // convert DataStream into Table with field "myLong" only
        Table table1 = tableEnv.fromDataStream(stream, $("myLong"));

        // convert DataStream into Table with field names "myLong" and "myInt"
        Table table2 = tableEnv.fromDataStream(stream, $("myLong"), $("myInt"));

        // 交换字段顺序
        Table table3 = tableEnv.fromDataStream(stream, $("f1").as("myInt"), $("f0").as("myLong"));
    }

    public void pojoStream2TableSchema() {
        StreamTableEnvironment tableEnv = null;

        // Person is a POJO with fields "name" and "age"
        DataStream<Person> stream = null;

        // convert DataStream into Table with renamed fields "myAge", "myName" (name-based)
        Table table = tableEnv.fromDataStream(stream,
                $("age").as("myAge"),
                $("name").as("myName")
        );

        // convert DataStream into Table with projected field "name" (name-based)
        Table table1 = tableEnv.fromDataStream(stream, $("name"));

        // convert DataStream into Table with projected and renamed field "myName" (name-based)
        Table table2 = tableEnv.fromDataStream(stream, $("name").as("myName"));
    }



    public void rowStream2TableSchema() {
        StreamTableEnvironment tableEnv = null;

        // DataStream of Row with two fields "name" and "age" specified in `RowTypeInfo`
        DataStream<Row> stream = null;

        // Convert DataStream into Table with renamed field names "myName", "myAge" (position-based)
        Table table = tableEnv.fromDataStream(stream, $("myName"), $("myAge"));

        // Convert DataStream into Table with renamed fields "myName", "myAge" (name-based)
        Table table1 = tableEnv.fromDataStream(stream, $("name").as("myName"), $("age").as("myAge"));

        // Convert DataStream into Table with projected field "name" (name-based)
        Table table2 = tableEnv.fromDataStream(stream, $("name"));

        // Convert DataStream into Table with projected and renamed field "myName" (name-based)
        Table table3 = tableEnv.fromDataStream(stream, $("name").as("myName"));

    }
}
