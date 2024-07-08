package io.github.btpka3.first.flink.udf;

import org.apache.flink.table.functions.ScalarFunction;

/**
 * Scalar Function
 * @author dangqian.zll
 * @date 2024/7/2
 * @see <a href="https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/dev/table/functions/udfs/">User-defined Functions</a>
 */
public class MyUdf extends ScalarFunction {
    public String eval(String s, Integer begin, Integer end) {
        return s.substring(begin, end);
    }
}