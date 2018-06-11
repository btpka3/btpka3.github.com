package com.github.btpka3.first.rocksdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JacksonTest01 {

    ObjectMapper m = new ObjectMapper();

    @Test
    public void test01() throws IOException {

        Map<String, String> map = new HashMap<>();
        map.put("a", "a1");
        String s = m.writeValueAsString(map);
        System.out.println(s);
        Object o = m.readValue(s, Object.class);
        System.out.println(o.getClass());
        System.out.println(o);
    }


    @Test
    public void test02() throws IOException {

        A a = new A();
        a.setName("zhang3");
        String s = m.writeValueAsString(a);
        System.out.println(s);
        Object o = m.readValue(s, Object.class);
        System.out.println(o.getClass());
        System.out.println(o);
    }

    public static class A{
        private String  name ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
