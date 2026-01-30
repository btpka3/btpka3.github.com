package me.test.jdk.java.util;

import com.alibaba.fastjson.JSON;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

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


    /**
     * ⚠️ 需要增加JVM参数: --add-opens=java.base/java.util=ALL-UNNAMED
     */
    @Test
    public void debugInner() {
        int itemCount = 256;
        HashMap<String, String> map = new HashMap<>(itemCount / 2);
        for (int i = 0; i < itemCount; i++) {
            String s = RandomStringUtils.randomAlphabetic(16);
            map.put(s, s);
        }
        // HashMap.Node<K,V>[] table;
        Object[] table = (Object[]) ReflectionTestUtils.getField(map, "table");

        Assertions.assertEquals(512, table.length);
        int nodeNonNullCount = 0;

        Map<Integer, AtomicInteger> nodeLinkLengthMap = new HashMap<>();

        for (Object node : table) {
            if (node == null) {
                continue;
            }
            nodeNonNullCount++;
            int linkLength = findNodeLinkLength(node);
            nodeLinkLengthMap.computeIfAbsent(linkLength, k -> new AtomicInteger(0))
                    .incrementAndGet();
        }

        System.out.println("nodeNonNullCount        = " + nodeNonNullCount);
        System.out.println("nodeLinkLengthMap       = " + nodeLinkLengthMap);

        int count = nodeLinkLengthMap.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue().get())
                .sum();
        Assertions.assertEquals(itemCount, count);
    }

    protected int findNodeLinkLength(@Nonnull Object hashMapNode) {
        int i = 0;
        Object node = hashMapNode;
        while (node != null) {
            i++;
            node = ReflectionTestUtils.getField(node, "next");
        }
        return i;
    }


}
