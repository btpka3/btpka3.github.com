package io.github.btpka3.first.flink;

import org.apache.flink.table.catalog.DataTypeFactory;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class DataTypeFactoryTest {

    public void x() {
        DataTypeFactory f = null;

        f.createDataType(Integer.class);
    }
}
