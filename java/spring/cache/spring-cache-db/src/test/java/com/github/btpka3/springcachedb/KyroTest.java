package com.github.btpka3.springcachedb;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 当千
 * @date 2018-08-30
 */
public class KyroTest {

    @Test
    public void a() {
        Map<String, Map<String, String>> root = new HashMap<>();
        Map<String, String> childMap = new HashMap<>();
        childMap.put("a", "aa");
        childMap.put("b", "bb");

        root.put("x", childMap);
        root.put("y", childMap);


        Kryo kryo = new Kryo();
        kryo.register(HashMap.class);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Output output = new Output(out);
        kryo.writeObject(output, root);
        output.close();

        System.out.println(out.toByteArray().length);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        Input input = new Input(in);
        Map<String, Map<String, String>> deRoot = kryo.readObject(input, HashMap.class);

        Assert.assertTrue(deRoot.containsKey("x"));
        Assert.assertTrue(deRoot.containsKey("y"));
        Assert.assertEquals("aa", deRoot.get("x").get("a"));
        Assert.assertEquals("bb", deRoot.get("y").get("b"));
        Assert.assertTrue(deRoot.get("x") == deRoot.get("y"));


    }
}
