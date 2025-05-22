package me.test.jdk.java.util;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Map.entry;

/**
 * @author dangqian.zll
 * @date 2020-03-16
 */
public class MapTest {

    public static void main(String[] args) {
        Map<Integer, String> rankedRuleMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        rankedRuleMap.put(0, "aa");
        rankedRuleMap.put(999, "dd");
        rankedRuleMap.put(100, "cc");
        rankedRuleMap.put(50, "bb");

        for (Map.Entry<Integer, String> entry : rankedRuleMap.entrySet()) {
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }
        System.out.println(rankedRuleMap);

    }

    @Test
    public void x() {
        Map<String, Object> m = new HashMap<>(6);
        m.put("a", "aaa");
        m.put("buyerIdentityNo", null);
        String idNo = m.getOrDefault("buyerIdentityNo", "").toString();
        Assertions.assertEquals("", idNo);
        Assertions.assertEquals("{\"a\":\"aaa\"}", JSON.toJSONString(m));
    }

    @Test
    public void of01() {
        Map<String, Object> map1 = Map.of(
                "k1", "v1",
                "k2", "v2",
                "k3", "v3"

        );
        Map<String, Object> map2 = Map.ofEntries(
                entry("k1", "v1"),
                entry("k2", "v2"),
                entry("k3", "v3")

        );
    }
}
