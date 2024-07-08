package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyFlatMapFunction implements FlatMapFunction<String, String> {
    public void flatMap(String value, Collector<String> collector) {
        collector.collect(value);
    }
}
