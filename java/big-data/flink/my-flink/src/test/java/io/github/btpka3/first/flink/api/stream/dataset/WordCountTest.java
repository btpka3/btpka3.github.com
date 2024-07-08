package io.github.btpka3.first.flink.api.stream.dataset;

import lombok.SneakyThrows;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class WordCountTest {


    @Test
    @SneakyThrows
    public void testWordCount() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        List<String> lines = Arrays.asList(
                "This is a first sentence",
                "This is a second sentence with a one word"
        );

        WordCountTest WordCount;
        DataSet<Tuple2<String, Integer>> result = startWordCount(env, lines);

        List<Tuple2<String, Integer>> collect = result.collect();

        assertThat(collect).containsExactlyInAnyOrder(
                new Tuple2<>("a", 3), new Tuple2<>("sentence", 2), new Tuple2<>("word", 1),
                new Tuple2<>("is", 2), new Tuple2<>("this", 2), new Tuple2<>("second", 1),
                new Tuple2<>("first", 1), new Tuple2<>("with", 1), new Tuple2<>("one", 1)
        );
    }



    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            Stream.of(value.toLowerCase().split("\\W+"))
                    .filter(t -> t.length() > 0)
                    .forEach(token -> out.collect(new Tuple2<>(token, 1)));
        }
    }

    public static DataSet<Tuple2<String, Integer>> startWordCount(
            ExecutionEnvironment env,
            List<String> lines
    ) throws Exception {
        DataSet<String> text = env.fromCollection(lines);

        return text.flatMap(new LineSplitter())
                .groupBy(0)
                .aggregate(Aggregations.SUM, 1);
    }
}
