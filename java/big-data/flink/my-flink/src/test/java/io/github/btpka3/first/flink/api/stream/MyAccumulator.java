package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.common.accumulators.SimpleAccumulator;

/**
 * 需要配合 org.apache.flink.api.common.functions.RichFunction#getRuntimeContext() 来注册使用。
 *
 * @author dangqian.zll
 * @date 2024/7/5
 * @see org.apache.flink.api.common.accumulators.Accumulator
 * @see org.apache.flink.api.common.accumulators.SimpleAccumulator
 * @see org.apache.flink.api.common.accumulators.IntCounter
 * @see org.apache.flink.api.common.accumulators.IntMaximum
 */
public class MyAccumulator implements SimpleAccumulator<String> {
    public static String DEFAULT_ACC_NAME = "myAggr";

    @Override
    public void add(String value) {

    }

    @Override
    public String getLocalValue() {
        return null;
    }

    @Override
    public void resetLocal() {

    }

    @Override
    public void merge(Accumulator other) {

    }

    @Override
    public Accumulator clone() {
        return null;
    }
}
