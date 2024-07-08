package io.github.btpka3.first.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.test.junit5.MiniClusterExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
@ExtendWith(MiniClusterExtension.class)
public class DataTypeTest {

    @Test
    public void test01() {
        Configuration configuration = null;
        TableEnvironment tableEnv = TableEnvironment.create(configuration);

        System.out.println(DataTypes.of(Integer.class));
        System.out.println(DataTypes.of(String.class));
        System.out.println(DataTypes.of(Date.class));
        System.out.println(DataTypes.of(Instant.class));
        DataTypeFactory dataTypeFactory = null;
        System.out.println(DataTypes.of(java.time.LocalDateTime.class).toDataType(dataTypeFactory));
    }
}
