package io.github.btpka3.first.flink;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;

/**
 * @author dangqian.zll
 * @date 2024/2/21
 * @see org.apache.flink.examples.java.wordcount.WordCount
 * @see org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.8/dev/projectsetup/java_api_quickstart.html#requirements-1">Project Template for Java</a>
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.8/tutorials/datastream_api.html">DataStream API Tutorial</a>
 */
public class MyWikipediaAnalysis {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<WikipediaEditEvent> edits = env.addSource(new WikipediaEditsSource());

        KeyedStream<WikipediaEditEvent, String> keyedEdits = edits
                .keyBy(new KeySelector<WikipediaEditEvent, String>() {
                    @Override
                    public String getKey(WikipediaEditEvent event) {
                        return event.getUser();
                    }
                });

        DataStream<Tuple2<String, Long>> result = keyedEdits
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .aggregate(new AggregateFunction<WikipediaEditEvent, Tuple2<String, Long>, Tuple2<String, Long>>() {

                    @Override
                    public Tuple2<String, Long> createAccumulator() {
                        return new Tuple2<>("", 0L);
                    }

                    @Override
                    public Tuple2<String, Long> add(WikipediaEditEvent event, Tuple2<String, Long> acc) {
                        acc.f0 = event.getUser();
                        acc.f1 += event.getByteDiff();
                        return acc;
                    }

                    @Override
                    public Tuple2<String, Long> getResult(Tuple2<String, Long> acc) {
                        return acc;
                    }

                    @Override
                    public Tuple2<String, Long> merge(Tuple2<String, Long> o, Tuple2<String, Long> acc1) {
                        return null;
                    }
                });

        result.print();

        env.execute();
    }
}
