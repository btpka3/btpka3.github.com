package me.test.jdk.java.util.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConcurrentHashMapTest {

    @Test
    public void testPutNullKey() {

        try {
            Map m = new ConcurrentHashMap();
            m.put(null, "aa");
            Assertions.fail("should throw NPE");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void testPutNullValue() {

        try {
            Map m = new ConcurrentHashMap();
            m.put("aa", null);
            Assertions.fail("should throw NPE");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void testPutNullValue2() {
        Map m = new ConcurrentHashMap();
        m.computeIfAbsent("aa", k -> null);
        System.out.println(m.containsKey("aa"));

    }

    @Test
    public void getNull01() {
        try {
            Map m = new ConcurrentHashMap();
            m.get(null);
            Assertions.fail("should throw NPE");
        } catch (NullPointerException e) {
        }
    }
}
