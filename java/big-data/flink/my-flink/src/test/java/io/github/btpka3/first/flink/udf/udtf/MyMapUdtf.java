package io.github.btpka3.first.flink.udf.udtf;

import org.apache.commons.collections.MapUtils;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;

import java.util.Map;

/**
 * Table Function: 入参，出参都是 MAP 类型.
 * <p>
 * 但 Map 的 Key,VALUE 不能是 java.lang.Object 类型。不够通用。
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
//@DataTypeHint("ROW<name STRING, age INT>")
public class MyMapUdtf extends TableFunction<Map<String, String>> {

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    public void eval(Map<String, String> map) {
        Integer age = MapUtils.getInteger(map, "age");
        if (age != null) {
            map.put("age", age + "300");
        }
        collect(map);
    }
}
