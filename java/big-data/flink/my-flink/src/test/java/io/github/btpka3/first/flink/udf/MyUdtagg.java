package io.github.btpka3.first.flink.udf;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.functions.TableAggregateFunction;
import org.apache.flink.util.Collector;

/**
 * Table Aggregate Functions
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
public class MyUdtagg extends TableAggregateFunction<Tuple2<Integer, Integer>, MyUdtagg.Top2Accumulator> {

    @Override
    public Top2Accumulator createAccumulator() {
        Top2Accumulator acc = new Top2Accumulator();
        acc.first = Integer.MIN_VALUE;
        acc.second = Integer.MIN_VALUE;
        return acc;
    }

    public void accumulate(Top2Accumulator acc, Integer value) {
        if (value > acc.first) {
            acc.second = acc.first;
            acc.first = value;
        } else if (value > acc.second) {
            acc.second = value;
        }
    }

    public void merge(Top2Accumulator acc, Iterable<Top2Accumulator> it) {
        for (Top2Accumulator otherAcc : it) {
            accumulate(acc, otherAcc.first);
            accumulate(acc, otherAcc.second);
        }
    }

    public void emitValue(Top2Accumulator acc, Collector<Tuple2<Integer, Integer>> out) {
        // emit the value and rank
        if (acc.first != Integer.MIN_VALUE) {
            out.collect(Tuple2.of(acc.first, 1));
        }
        if (acc.second != Integer.MIN_VALUE) {
            out.collect(Tuple2.of(acc.second, 2));
        }
    }

    public static class Top2Accumulator {
        public Integer first;
        public Integer second;
    }
}
