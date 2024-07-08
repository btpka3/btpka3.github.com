package io.github.btpka3.first.flink.udf.hive;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.table.functions.hive.HiveGenericUDAF;
import org.apache.flink.table.functions.hive.HiveGenericUDF;
import org.apache.flink.types.Row;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFResolver2;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.flink.table.functions.hive.HiveGenericUDTF;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
public class MyGenericUDTF extends GenericUDTF {

    public void x(){
        // hive 相关原生类

        UDF udf;
        UDAF udaf;
        GenericUDF genericUDF;
        GenericUDTF genericUDTF;
        GenericUDAFResolver2 genericUDAFResolver2;

        // apache flink -> hive 的桥接类

        HiveGenericUDF hiveGenericUDF;
        HiveGenericUDTF hiveGenericUDTF;
        HiveGenericUDAF hiveGenericUDAF;
    }

    @Override
    public void process(Object[] objects) throws HiveException {

    }

    @Override
    public void close() throws HiveException {

    }
}
