package io.github.btpka3.first.flink.api.stream.dataset;

import lombok.*;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class MapTest {

    @Test
    @SneakyThrows
    public void testMap() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Person> personDataSource = env.fromCollection(
                Arrays.asList(
                        new Person(23, "Tom"),
                        new Person(75, "Michael")
                )
        );

        List<Integer> ages = personDataSource
                .map(p -> p.age)
                .collect();

        assertThat(ages).hasSize(2);
        assertThat(ages).contains(23, 75);
    }


    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private int age;
        private String name;
    }

}
