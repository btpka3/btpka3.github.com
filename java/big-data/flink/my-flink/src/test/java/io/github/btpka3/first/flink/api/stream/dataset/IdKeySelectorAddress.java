package io.github.btpka3.first.flink.api.stream.dataset;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class IdKeySelectorAddress
        implements KeySelector<Tuple3<Integer, String, String>, Integer> {
    @Override
    public Integer getKey(Tuple3<Integer, String, String> value) {
        return value.f0;
    }
}
