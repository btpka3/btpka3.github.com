package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.functions.AggregateFunction;

/**
 * Aggregate Functions
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
public class MyUdagg extends AggregateFunction<Long, MyUdagg.WeightedAvgAccumulator> {

    @Override
    public WeightedAvgAccumulator createAccumulator() {
        return new WeightedAvgAccumulator();
    }

    @Override
    public Long getValue(WeightedAvgAccumulator acc) {
        if (acc.count == 0) {
            return null;
        } else {
            return acc.sum / acc.count;
        }
    }

    public void accumulate(WeightedAvgAccumulator acc, Long iValue, Integer iWeight) {
        acc.sum += iValue * iWeight;
        acc.count += iWeight;
    }

    public void retract(WeightedAvgAccumulator acc, Long iValue, Integer iWeight) {
        acc.sum -= iValue * iWeight;
        acc.count -= iWeight;
    }

    public void merge(WeightedAvgAccumulator acc, Iterable<WeightedAvgAccumulator> it) {
        for (WeightedAvgAccumulator a : it) {
            acc.count += a.count;
            acc.sum += a.sum;
        }
    }

    public void resetAccumulator(WeightedAvgAccumulator acc) {
        acc.count = 0;
        acc.sum = 0L;
    }

    public static class WeightedAvgAccumulator {
        public long sum = 0;
        public int count = 0;
    }


}
