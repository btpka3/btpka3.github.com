package io.github.btpka3.first.flink.api.stream.dataset;

import lombok.SneakyThrows;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class FilterAndReduceTest {

    @Test
    @SneakyThrows
    public void testFilterAndReduce() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Integer> amounts = env.fromElements(1, 29, 40, 50);
        int threshold = 30;
        List<Integer> collect = amounts
                .filter(a -> a > threshold)
                .reduce((integer, t1) -> integer + t1)
                .collect();
        assertEquals(90, collect.get(0));
    }
}
