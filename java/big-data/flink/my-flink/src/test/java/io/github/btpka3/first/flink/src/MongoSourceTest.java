package io.github.btpka3.first.flink.src;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.connector.mongodb.source.MongoSource;
import org.apache.flink.connector.mongodb.source.enumerator.splitter.PartitionStrategy;
import org.apache.flink.connector.mongodb.source.reader.deserializer.MongoDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.bson.BsonDocument;

/**
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/connectors/datastream/mongodb/">MongoDB Connector</a>
 */
public class MongoSourceTest {

    public void x(){
        MongoSource<String> source = MongoSource.<String>builder()
                .setUri("mongodb://user:password@127.0.0.1:27017")
                .setDatabase("my_db")
                .setCollection("my_coll")
                .setProjectedFields("_id", "f0", "f1")
                .setFetchSize(2048)
                .setLimit(10000)
                .setNoCursorTimeout(true)
                .setPartitionStrategy(PartitionStrategy.SAMPLE)
                .setPartitionSize(MemorySize.ofMebiBytes(64))
                .setSamplesPerPartition(10)
                .setDeserializationSchema(new MongoDeserializationSchema<String>() {
                    @Override
                    public String deserialize(BsonDocument document) {
                        return document.toJson();
                    }

                    @Override
                    public TypeInformation<String> getProducedType() {
                        return BasicTypeInfo.STRING_TYPE_INFO;
                    }
                })
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "MongoDB-Source")
                .setParallelism(2)
                .print()
                .setParallelism(1);
    }
}
