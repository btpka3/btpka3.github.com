package io.github.btpka3.first.flink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.CustomSinkOperatorUidHashes;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.File;

/**
 * @author dangqian.zll
 * @date 2024/2/21
 */
public class DataStreamTest {

    public void filter01() {
        FileSource<String> fs = FileSource.forRecordStreamFormat(new TextLineInputFormat(), Path.fromLocalFile(new File("/path/to/file")))
                .build();
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> ds = env.fromSource(fs, WatermarkStrategy.noWatermarks(), "file");
        int threshold = 30;
        DataStream<String> ds1 = ds.map(Integer::valueOf)
                .filter(a -> a > threshold)
                .map(Object::toString);

        Sink<String> sink = FileSink.forRowFormat(
                        Path.fromLocalFile(new File("/path/to/file")),
                        new SimpleStringEncoder<String>()
                )
                .build();
        DataStreamSink.forSink(ds1, sink, CustomSinkOperatorUidHashes.builder().build());

    }

}
