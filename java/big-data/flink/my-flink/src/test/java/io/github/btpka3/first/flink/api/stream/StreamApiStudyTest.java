package io.github.btpka3.first.flink.api.stream;

import lombok.SneakyThrows;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 * @see <a href="https://www.baeldung.com/apache-flink">Introduction to Apache Flink with Java</a>
 */
public class StreamApiStudyTest {

    public void test01() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> input = env.readTextFile("file:///path/to/file");
        DataStream<Integer> parsed = input.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) {
                return Integer.parseInt(value);
            }
        });

        final JobClient jobClient = env.executeAsync();

        final JobExecutionResult jobExecutionResult = jobClient.getJobExecutionResult().get();
    }


    public void test02() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap(new Splitter()).name("xxx")
                .keyBy(value -> value.f0)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                .sum(1);

        dataStream.print();

        env.execute("Window WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }

    /**
     * 检查控制台有输出给定文本的大写case
     * <p>
     * 执行并检查 标准输出：正则搜索 `\d> `
     */
    @Test
    @SneakyThrows
    public void testStream1() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromElements(
                "This is a first sentence",
                "This is a second sentence with a one word"
        );
        SingleOutputStreamOperator<String> upperCase = dataStream
                .map(String::toUpperCase);
        upperCase.print();
        JobExecutionResult jobExecutionResult = env.execute();

        /* 示例输出:
            9> THIS IS A FIRST SENTENCE
            10> THIS IS A SECOND SENTENCE WITH A ONE WORD
         */
    }

    /**
     * 执行并检查 标准输出：正则搜索 `\d> \(`
     */
    @Test
    @SneakyThrows
    public void testStreamWindow1() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<Tuple2<Integer, Long>> windowed = env.fromElements(
                        new Tuple2<>(16, ZonedDateTime.now().plusMinutes(25).toInstant().getEpochSecond()),
                        new Tuple2<>(15, ZonedDateTime.now().plusMinutes(2).toInstant().getEpochSecond())
                )
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Tuple2<Integer, Long>>(Time.seconds(20)) {
                    @Override
                    public long extractTimestamp(Tuple2<Integer, Long> element) {
                        return element.f1 * 1000;
                    }
                });

        SingleOutputStreamOperator<Tuple2<Integer, Long>> reduced = windowed
                .windowAll(TumblingEventTimeWindows.of(Time.seconds(5)))
                .maxBy(0, true);

        reduced.print();
        JobExecutionResult jobExecutionResult = env.execute();

        /* 示例输出:
            5> (15,1720168773)
            6> (16,1720170153)
         */
    }

    public void testEnv() {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        {
            env.setBufferTimeout(1000L);
            // 流模式/批模式
            env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        }


        Configuration configuration = new Configuration();
        StreamExecutionEnvironment env1 = StreamExecutionEnvironment.getExecutionEnvironment(configuration);


        StreamExecutionEnvironment localEnv = StreamExecutionEnvironment.createLocalEnvironment();

        String host = "127.0.0.1";
        int port = 88;
        String[] jarFiles = null;
        StreamExecutionEnvironment remoteEnv = StreamExecutionEnvironment.createRemoteEnvironment(host, port, jarFiles);
    }


    public void testSource() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        {
            DataStreamSource<String> dataStreamSource = env.fromCollection(Arrays.asList("aaa", "bbb", "ccc"));
        }
        {
            Iterator<String> longIt = null;
            DataStream<String> dataStream = env.fromCollection(longIt, String.class);
        }

        {
            FileSource<String> source = FileSource.forRecordStreamFormat(
                            new TextLineInputFormat(),
                            new Path("/ foo/ bar")
                    )
                    .build();
            WatermarkStrategy watermarkStrategy = WatermarkStrategy.noWatermarks();
            DataStreamSource dataStreamSource = env.fromSource(source, watermarkStrategy, "xxx");
        }
        {
            String hostname = null;
            int port = 0;
            String delimiter = "\n";
            long maxRetry = 0;
            DataStream<String> dataStream = env.socketTextStream(hostname, port, delimiter, maxRetry);

        }

        {
            KeyedStream s;
        }
    }

    public void testSink() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromCollection(Arrays.asList("aaa", "bbb", "ccc"));


        {
            String hostName = null;
            int port = 0;
            SerializationSchema<String> schema = null;
            dataStream.writeToSocket(hostName, port, schema);
        }
        {
            Sink<String> sink = FileSink.forRowFormat(
                            Path.fromLocalFile(new File("/path/to/file")),
                            new SimpleStringEncoder<String>()
                    )
                    .build();
            //dataStream.addSink(sink);
        }
        {
            dataStream.print();
        }
        {
            Iterator<String> myOutput = dataStream.collectAsync();
        }


    }

    public void testWatermarkStrategy() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromCollection(Arrays.asList("aaa", "bbb", "ccc"));

        WatermarkStrategy watermarkStrategy = null;
        watermarkStrategy = WatermarkStrategy.noWatermarks();
        watermarkStrategy = WatermarkStrategy.forMonotonousTimestamps();
        watermarkStrategy = WatermarkStrategy.<Tuple2<Long, String>>forBoundedOutOfOrderness(Duration.ofSeconds(20))
                .withTimestampAssigner((event, timestamp) -> event.f0);

        dataStream = dataStream.assignTimestampsAndWatermarks(watermarkStrategy);
        dataStream.keyBy(s -> s)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
        ;

    }

    public void broadcast() {

        // Item : 数据
        // Long : key


        DataStream<Item> itemStream = null;
        KeySelector<Item, Color> ks = null;
        KeyedStream<Item, Color> colorPartitionedStream = itemStream.keyBy(ks);


        DataStream<Rule> ruleStream = null;
        MapStateDescriptor<String, Rule> ruleStateDescriptor = new MapStateDescriptor<>(
                "RulesBroadcastState",
                BasicTypeInfo.STRING_TYPE_INFO,
                TypeInformation.of(new TypeHint<Rule>() {
                }));

        // 广播流，广播规则并且创建 broadcast state
        BroadcastStream<Rule> ruleBroadcastStream = ruleStream
                .broadcast(ruleStateDescriptor);

        BroadcastConnectedStream<Item, Rule> c = colorPartitionedStream
                .connect(ruleBroadcastStream);

        // KeyedBroadcastProcessFunction 中的类型参数表示：
        //   1. key stream 中的 key 类型
        //   2. 非广播流中的元素类型
        //   3. 广播流中的元素类型
        //   4. 结果的类型，在这里是 string
        KeyedBroadcastProcessFunction<Color, Item, Rule, String> ff = null;
        DataStream<String> output = c
                .process(ff);
    }

    public static class Item {

    }

    public static class Rule {

    }

    public static class Color {

    }

}
