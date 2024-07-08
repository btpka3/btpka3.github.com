package io.github.btpka3.first.flink.udf;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author dangqian.zll
 * @date 2024/7/3
 */
public class IncrementMapFunction implements MapFunction<Long, Long> {

    @Override
    public Long map(Long record) throws Exception {
        return record + 1;
    }
}