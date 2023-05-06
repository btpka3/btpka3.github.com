package org.apache.storm.starter.streams;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.starter.spout.RandomIntegerSpout;
import org.apache.storm.streams.Pair;
import org.apache.storm.streams.StreamBuilder;
import org.apache.storm.streams.operations.CombinerAggregator;
import org.apache.storm.streams.operations.mappers.ValueMapper;
import org.apache.storm.streams.windowing.TumblingWindows;
import org.apache.storm.topology.base.BaseWindowedBolt;

/**
 * @author dangqian.zll
 * @date 2023/3/6
 * @see <a href="https://github.com/apache/storm/blob/v2.4.0/examples/storm-starter/src/jvm/org/apache/storm/starter/streams/AggregateExample.java">AggregateExample.java</a>
 */
public class AggregateExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        StreamBuilder builder = new StreamBuilder();
        /**
         * Computes average of the stream of numbers emitted by the spout. Internally the per-partition
         * sum and counts are accumulated and emitted to a downstream task where the partially accumulated
         * results are merged and the final result is emitted.
         */
        builder.newStream(new RandomIntegerSpout(), new ValueMapper<Integer>(0), 2)
                .window(TumblingWindows.of(BaseWindowedBolt.Duration.seconds(5)))
                .filter(x -> x > 0 && x < 500)
                .aggregate(new Avg())
                .print();

        Config config = new Config();
        String topoName = "AGG_EXAMPLE";
        if (args.length > 0) {
            topoName = args[0];
        }
        config.setNumWorkers(1);
        StormSubmitter.submitTopologyWithProgressBar(topoName, config, builder.build());
    }

    private static class Avg implements CombinerAggregator<Integer, Pair<Integer, Integer>, Double> {
        @Override
        public Pair<Integer, Integer> init() {
            return Pair.of(0, 0);
        }

        @Override
        public Pair<Integer, Integer> apply(Pair<Integer, Integer> sumAndCount, Integer value) {
            return Pair.of(sumAndCount.value1 + value, sumAndCount.value2 + 1);
        }

        @Override
        public Pair<Integer, Integer> merge(Pair<Integer, Integer> sumAndCount1, Pair<Integer, Integer> sumAndCount2) {
            System.out.println("Merge " + sumAndCount1 + " and " + sumAndCount2);
            return Pair.of(
                    sumAndCount1.value1 + sumAndCount2.value1,
                    sumAndCount1.value2 + sumAndCount2.value2
            );
        }

        @Override
        public Double result(Pair<Integer, Integer> sumAndCount) {
            return (double) sumAndCount.value1 / sumAndCount.value2;
        }
    }
}
