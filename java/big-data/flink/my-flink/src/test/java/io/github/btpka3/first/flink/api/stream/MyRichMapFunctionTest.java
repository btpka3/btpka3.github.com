package io.github.btpka3.first.flink.api.stream;

import org.apache.flink.streaming.api.datastream.DataStream;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyRichMapFunctionTest {

    public void x() {
        DataStream<String> dataStream = null;
        dataStream.map(new MyRichMapFunction());
    }
}
