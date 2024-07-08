package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.inference.TypeInference;
import org.apache.flink.types.Row;

import java.util.Optional;

/**
 * Table Function
 *
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
@FunctionHint(output = @DataTypeHint("ROW<word STRING, length INT>"))
public class MyUdtf extends TableFunction<Row> {

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);


    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    public void eval(String str) {
        for (String s : str.split(" ")) {
            // use collect(...) to emit a row

            collect(Row.of(s, s.length()));
        }
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        return TypeInference.newBuilder()
                .outputTypeStrategy(callContext -> Optional.of(
                        DataTypes.ROW(
                                DataTypes.INT(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING(),
                                DataTypes.STRING()
                        )
                )).build();
    }

    @Deprecated
    public DataType getResultType(Object[] arguments, Class[] argTypes) {
        return DataTypes.ROW(
                DataTypes.FIELD("word", DataTypes.STRING(),"sss")
        );
    }
}
