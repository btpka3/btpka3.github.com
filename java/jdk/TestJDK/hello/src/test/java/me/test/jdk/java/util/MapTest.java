package me.test.jdk.java.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

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
}
