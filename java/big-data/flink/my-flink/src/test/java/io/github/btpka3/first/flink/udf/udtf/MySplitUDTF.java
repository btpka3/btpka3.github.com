package io.github.btpka3.first.flink.udf.udtf;

import org.apache.flink.table.functions.TableFunction;

/**
 * @author dangqian.zll
 * @date 2024/7/9
 */
public class MySplitUDTF extends TableFunction<String> {
    public void eval(String str) {
        String[] arr = str.split(",");
        for (String s : arr) {
            this.collect(s);
        }
    }
}
