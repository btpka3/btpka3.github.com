package io.github.btpka3.first.flink.api.stream;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2024/7/8
 */
public class DemoStreamTest {

    @Test
    @SneakyThrows
    public void executeAndCollect01() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> ds = env.fromCollection(Arrays.asList("aaa", "bbb", "ccc"));
        List<String> resultList = ds.map(s -> s + "1")
                .executeAndCollect("myJob", 100);
        System.out.println("resultList = " + resultList);
        // 不保证顺序
        assertEquals(new HashSet<>(Arrays.asList("aaa1", "bbb1", "ccc1")), new HashSet<>(resultList));
    }
}
