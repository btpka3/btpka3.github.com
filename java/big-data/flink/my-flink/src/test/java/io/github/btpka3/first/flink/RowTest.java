package io.github.btpka3.first.flink;

import org.apache.flink.types.Row;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/7/3
 */
public class RowTest {

    public Row mapToRow(Map<String, Object> map) {
        Row row = Row.withNames();
        map.forEach(row::setField);
        return row;
    }
}
