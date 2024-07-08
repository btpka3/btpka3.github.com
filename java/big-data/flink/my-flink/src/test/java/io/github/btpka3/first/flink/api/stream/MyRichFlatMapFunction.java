package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyRichFlatMapFunction extends RichFlatMapFunction<String, String> {
    @Override
    public void flatMap(String value, Collector<String> out) throws Exception {

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }
}
