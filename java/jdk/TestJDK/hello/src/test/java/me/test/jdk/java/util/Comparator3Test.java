package me.test.jdk.java.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/9/22
 */
public class Comparator3Test {

    @Test
    public void test01() {

        List<Info> list = Arrays.asList(
                Info.builder()
                        .id("ccc")
                        .keys(Arrays.asList(
                                Key.builder().name("k1").value("v11").build(),
                                Key.builder().name("k2").value("v21").build()))
                        .build(),
                Info.builder()
                        .id("aaa")
                        .keys(Arrays.asList(
                                Key.builder().name("k1").value("v10").build(),
                                Key.builder().name("k2").value("v20").build()))
                        .build(),
                Info.builder()
                        .id("bbb")
                        .keys(Arrays.asList(
                                Key.builder().name("k1").value("v11").build(),
                                Key.builder().name("k2").value("v20").build()))
                        .build());

        Comparator.comparing(Key::getName).thenComparing(Key::getValue);
        Comparator<Key> keyComparator =
                Comparator.nullsLast(Comparator.comparing(Key::getName).thenComparing(Key::getValue));
        Comparator<List<Key>> keyListComparator = (l1, l2) -> {
            int maxSize = Math.max(l1.size(), l2.size());
            for (int i = 0; i < maxSize; i++) {
                Key key1 = i < l1.size() ? l1.get(i) : null;
                Key key2 = i < l2.size() ? l2.get(i) : null;
                int result = keyComparator.compare(key1, key2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
        Comparator.comparing(Key::getName).thenComparing(Key::getValue);
        Collections.sort(list, Comparator.comparing(Info::getKeys, keyListComparator));
        String ids = list.stream().map(Info::getId).collect(Collectors.joining(","));
        Assertions.assertEquals("aaa,bbb,ccc", ids);
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private String id;
        private List<Key> keys;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key {

        private String name;
        private String value;
    }
}
