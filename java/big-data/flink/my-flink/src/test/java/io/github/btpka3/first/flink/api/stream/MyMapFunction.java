package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyMapFunction implements MapFunction<String, Integer> {
    public Integer map(String value) { return Integer.parseInt(value); }
}
