package io.github.btpka3.first.flink.api.stream.dataset;

import lombok.SneakyThrows;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class JoinTest {
    @Test
    @SneakyThrows
    public void testJoin() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        Tuple3<Integer, String, String> address = new Tuple3<>(1, "5th Avenue", "London");
        DataSet<Tuple3<Integer, String, String>> addresses = env.fromElements(address);

        Tuple2<Integer, String> firstTransaction = new Tuple2<>(1, "Transaction_1");
        Tuple2<Integer, String> secondTransaction = new Tuple2<>(12, "Transaction_2");
        DataSet<Tuple2<Integer, String>> transactions = env.fromElements(firstTransaction, secondTransaction);

        List<Tuple2<Tuple2<Integer, String>, Tuple3<Integer, String, String>>>
                joined = transactions.join(addresses)
                .where(new IdKeySelectorTransaction())
                .equalTo(new IdKeySelectorAddress())
                .collect();

        assertThat(joined).hasSize(1);
        assertThat(joined).contains(new Tuple2<>(firstTransaction, address));
    }

}
