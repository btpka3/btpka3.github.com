package org.apache.storm.starter.streams;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.streams.Pair;
import org.apache.storm.streams.StreamBuilder;
import org.apache.storm.streams.operations.mappers.ValueMapper;
import org.apache.storm.streams.windowing.TumblingWindows;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.base.BaseWindowedBolt;

/**
 * @author dangqian.zll
 * @date 2023/3/6
 * @see <a href="https://github.com/apache/storm/blob/v2.4.0/examples/storm-starter/src/jvm/org/apache/storm/starter/streams/StatefulWordCount.java">StatefulWordCount.java</a>
 */
public class StatefulWordCount {
    public static void main(String[] args) throws Exception {
        StreamBuilder builder = new StreamBuilder();
        // a stream of words
        builder.newStream(new TestWordSpout(), new ValueMapper<String>(0), 2)
                .window(TumblingWindows.of(BaseWindowedBolt.Duration.seconds(2)))
                /*
                 * create a stream of (word, 1) pairs
                 */
                .mapToPair(w -> Pair.of(w, 1))
                /*
                 * compute the word counts in the last two second window
                 */
                .countByKey()
                /*
                 * update the word counts in the state.
                 * Here the first argument 0L is the initial value for the state
                 * and the second argument is a function that adds the count to the current value in the state.
                 */
                .updateStateByKey(0L, (state, count) -> state + count)
                /*
                 * convert the state back to a stream and print the results
                 */
                .toPairStream()
                .print();

        Config config = new Config();
        // use redis based state store for persistence
        config.put(Config.TOPOLOGY_STATE_PROVIDER, "org.apache.storm.redis.state.RedisKeyValueStateProvider");
        String topoName = "test";
        if (args.length > 0) {
            topoName = args[0];
        }
        config.setNumWorkers(1);
        StormSubmitter.submitTopologyWithProgressBar(topoName, config, builder.build());
    }
}
