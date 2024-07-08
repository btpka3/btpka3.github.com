package io.github.btpka3.first.flink.udf.udtf;

import io.github.btpka3.first.flink.udf.Person;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

import java.util.Objects;

/**
 * Table Function: 入参，出参都是 ROW类型，内部按POJO处理
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
public class MyPersonUdtf extends TableFunction<Person> {

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    public void eval(Person person) {
        if (person.getAge() != null) {
            person.setAge(person.getAge() + 100);
        }
        collect(person);
    }
    protected Person row2Person(Row row) {
        return Person.builder()
                .name(toString(row.getField("name")))
                .age(toInt(row.getField("age")))
                .build();
    }

    protected String toString(Object obj) {
        return Objects.toString(obj, null);
    }

    protected Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return Integer.valueOf(obj.toString());
    }
}
