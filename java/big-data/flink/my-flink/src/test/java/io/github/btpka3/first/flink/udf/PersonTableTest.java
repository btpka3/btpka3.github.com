package io.github.btpka3.first.flink.udf;

import lombok.SneakyThrows;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.types.UnresolvedDataType;
import org.apache.flink.table.typeutils.FieldInfoUtils;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.$;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class PersonTableTest {

    /**
     * console 控制台 正则搜索: `\d `
     */
    @SneakyThrows
    @Test
    public void stream2Table01() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);


        DataStream<Person> stream = env.fromElements(
                Person.builder()
                        .name("zhang3")
                        .age(33)
                        .build(),
                Person.builder()
                        .name("li4")
                        .age(44)
                        .build(),
                Person.builder()
                        .name("wang5")
                        .age(55)
                        .build()
        );
        System.out.println("stream.getType() == " + stream.getType());

        // convert DataStream into Table with renamed fields "myAge", "myName" (name-based)
        Table table = tEnv.fromDataStream(stream,
                $("age").as("myAge"),
                $("name").as("myName")
        );

        System.out.println("===== table.getResolvedSchema() : " + table.getResolvedSchema());

        DataStream<Row> dataStream = tEnv.toAppendStream(table, Row.class);
        List<Row> list = dataStream.executeAndCollect("myJob", 100);
        System.out.println("==== list : " + list);
    }

    @SneakyThrows
    @Test
    public void sssa() {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();
        TableEnvironment tEnv = TableEnvironment.create(settings);


        ROW(
                FIELD("name", STRING()),
                FIELD("age", INT())
        );

        TypeInformation typeInfo = Types.POJO(MyPojo.class);
        FieldInfoUtils.TypeInfoSchema typeInfoSchema = FieldInfoUtils.getFieldsInfo(typeInfo);
        ResolvedSchema resolvedSchema = typeInfoSchema.toResolvedSchema();
        UnresolvedDataType dataType = of(typeInfo);
        System.out.println("==== dataType : " + dataType);

        MyPojo myPojo = new MyPojo();
        myPojo.setName("zhang3");
        myPojo.setAge(11);

        STRUCTURED(MyPojo.class);
        UnresolvedDataType dataType1 = of(MyPojo.class);
        Table table1 = tEnv.fromValues(
                dataType1,
                myPojo
        );

        System.out.println("==== table1.getResolvedSchema() : " + table1.getResolvedSchema());
    }

    @DataTypeHint("ROW")
    public static class MyPojo {
        private String name;
        public Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }


}
