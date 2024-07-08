package io.github.btpka3.first.flink.udf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.table.annotation.DataTypeHint;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@DataTypeHint("MAP<STRING, STRING>")
public class Person {

    @DataTypeHint("STRING")
    private String name;
    @DataTypeHint("INT")
    private Integer age;
}