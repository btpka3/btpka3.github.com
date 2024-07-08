package io.github.btpka3.first.flink.api.stream.dataset;

import lombok.SneakyThrows;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class SortTest {
    @Test
    @SneakyThrows
    public void testSort() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        Tuple2<Integer, String> secondPerson = new Tuple2<>(4, "Tom");
        Tuple2<Integer, String> thirdPerson = new Tuple2<>(5, "Scott");
        Tuple2<Integer, String> fourthPerson = new Tuple2<>(200, "Michael");
        Tuple2<Integer, String> firstPerson = new Tuple2<>(1, "Jack");
        DataSet<Tuple2<Integer, String>> transactions = env.fromElements(
                fourthPerson, secondPerson, thirdPerson, firstPerson);

        List<Tuple2<Integer, String>> sorted = transactions
                .sortPartition(new IdKeySelectorTransaction(), Order.ASCENDING)
                .collect();

        assertThat(sorted)
                .containsExactly(firstPerson, secondPerson, thirdPerson, fourthPerson);

    }
}
