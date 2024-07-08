package io.github.btpka3.first.flink.api.stream;

import lombok.SneakyThrows;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MyAccumulatorTest {

    @SneakyThrows
    public void x() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromCollection(Arrays.asList("aaa", "bbb", "ccc"));

        // 在 自定义 MyRichMapFunction 中注册 MyAccumulator
        dataStream.map(new MyRichMapFunction());

        // 正常进行数据处理
        JobExecutionResult jobExecutionResult = env.execute();

        String accName = MyAccumulator.DEFAULT_ACC_NAME;
        String myAccResult = jobExecutionResult.getAccumulatorResult(accName);
    }
}
