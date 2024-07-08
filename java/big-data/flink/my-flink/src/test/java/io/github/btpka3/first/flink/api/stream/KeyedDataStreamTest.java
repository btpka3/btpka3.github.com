package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class KeyedDataStreamTest {

    public void x() {
        DataStream<WC> dataStream = null;
        KeyedStream<WC, String> keyedStream = dataStream
                .keyBy(WC::getWord);
    }

    public static class WC {
        public String word;
        public int count;

        public String getWord() {
            return word;
        }
    }
}
