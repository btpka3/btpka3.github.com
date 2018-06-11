package com.github.btpka3.first.rocksdb;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class KryoTest {


    Logger logger = LoggerFactory.getLogger(KryoTest.class);


    @Test
    public void test01() {

        Map<String, String> map = new HashMap<>(2);
        map.put("a", "aa");
        map.put("b", "bb");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(111);
        Output output = new Output(out);

        Kryo kryo = new Kryo();
        kryo.writeObject(output, map);
        output.flush();

        byte[] arr = out.toByteArray();
        logger.debug("arr.length =" + arr.length);
    }
}
