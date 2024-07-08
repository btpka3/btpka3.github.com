package io.github.btpka3.first.flink.api.stream.dataset;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */

public class IdKeySelectorTransaction
        implements KeySelector<Tuple2<Integer, String>, Integer> {
    @Override
    public Integer getKey(Tuple2<Integer, String> value) {
        return value.f0;
    }
}