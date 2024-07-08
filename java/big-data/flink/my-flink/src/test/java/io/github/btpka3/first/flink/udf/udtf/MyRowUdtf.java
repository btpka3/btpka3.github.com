package io.github.btpka3.first.flink.udf.udtf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.table.types.inference.TypeInference;
import org.apache.flink.types.Row;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.apache.flink.table.api.DataTypes.*;

/**
 * Table Function: 入参，出参都是 ROW类型，内部按POJO处理
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
//@DataTypeHint("ROW<name STRING, age INT>")
public class MyRowUdtf extends TableFunction<Row> {

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    public void eval(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return;
        }
        JSONObject map = JSON.parseObject(jsonStr);
        Row row = mapToRow(map);
        processNameBasedRow(row);
        collect(row);
    }

    /**
     * 注意：由于使用 `row(value1,value2)` 函数，返回的 ROW 是 position-based , 而非 name-based.
     * 故不能使用  row.getField(String) 方式获取字段
     *
     * @param row
     */
    public void eval(Row row) {
        Object age = row.getField(1);
        if (age instanceof Number) {
            row.setField(1, ((Number) age).intValue() + 200);
        }
        processPositionBasedRow(row);
        collect(row);
    }

    public void eval(Map<String, Object> map) {
        Integer age = MapUtils.getInteger(map, "age");
        Row row = Row.withNames();
        map.forEach(row::setField);
        if (age != null) {
            map.put("age", age + "300");
        }
        processNameBasedRow(row);
        collect(row);
    }

    public void processPositionBasedRow(Row row) {

        Object age = row.getField(1);
        if (age instanceof Number) {
            row.setField(1, ((Number) age).intValue() + 200);
        }
    }

    public void processNameBasedRow(Row row) {
        Set<String> names = row.getFieldNames(true);
        Object age = row.getField("age");
        if (age instanceof Number) {
            row.setField("age", ((Number) age).intValue() + 200);
        }
    }

    public static Row mapToRow(Map<String, Object> map) {

        Row row = Row.withNames();
        //Row row = new Row(map.size());
        map.forEach(row::setField);
        return row;
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {

        return TypeInference.newBuilder()
                .outputTypeStrategy(callContext -> Optional.of(
                        ROW(
                                FIELD("name", STRING()),
                                FIELD("age", INT())
                        )
                )).build();
    }
}
