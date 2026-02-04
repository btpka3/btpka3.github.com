package me.test.jdk.java.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/6/4
 */
public class Comparator2Test {

    @Test
    public void testGroupSort01() {

        List<String> files = Arrays.asList(
                "/lib/bundle-sdk-1.0.91-shaded.jar",
                "/classes/C01.class",
                "/classes/C02.class",
                "/classes/C03.class",
                "/antx/A01.jar",
                "/antx/A02.jar",
                "/antx/A03.jar",
                "/lib/B01.jar",
                "/lib/B02.jar",
                "/lib/B03.jar",
                "/D01.jar",
                "/D02.jar",
                "/D03.jar");

        List<String> expected = new ArrayList<>(files);

        // 先打乱顺序。
        Collections.shuffle(files);
        assertNotEquals(expected, files);

        System.out.println("before :\n" + JSON.toJSONString(files, true));
        sort(files);
        System.out.println("after :\n" + JSON.toJSONString(files, true));

        assertEquals(expected, files);
    }

    @Test
    public void testCustom01() {

        List<String> files = Arrays.asList(
                "/lib/bundle-sdk-1.0.91-shaded.jar",
                "/D02.jar",
                "/antx/A02.jar",
                "/classes/C01.class",
                "/classes/C02.class",
                "/classes/C03.class",
                "/antx/A01.jar",
                "/antx/A03.jar",
                "/lib/B01.jar",
                "/lib/B02.jar",
                "/lib/B03.jar",
                "/D01.jar",
                "/D03.jar");
        List<String> expected = new ArrayList<>(files);

        List<String> customFileOrder = Arrays.asList("/D02.jar", "/antx/A02.jar");
        Map<String, Integer> customFileOrderMap = new HashMap<>(8);
        for (int i = 0; i < customFileOrder.size(); i++) {
            customFileOrderMap.put(customFileOrder.get(i), i);
        }

        // 先打乱顺序。
        Collections.shuffle(files);
        assertNotEquals(expected, files);

        System.out.println("before :\n" + JSON.toJSONString(files, true));
        sort(files, customFileOrderMap);
        System.out.println("after :\n" + JSON.toJSONString(files, true));

        assertEquals(expected, files);
    }

    void sort(List<String> files) {
        sort(files, Collections.emptyMap());
    }

    /**
     * 按照以下顺序排序。
     *
     * <ol>
     *     <li>/lib/bundle-sdk*.jar</li>
     *     <li>自定义jar的顺序</li>
     *     <li>/classes/* 【字符串自然序】</li>
     *     <li>/antx/* 【字符串自然序】</li>
     *     <li>/lib/* 【字符串自然序】</li>
     *     <li>其他 【字符串自然序】</li>
     * </ol>
     *
     * @return
     */
    void sort(List<String> files, Map<String, Integer> customFileOrderMap) {
        files.sort(Comparator.nullsLast(Comparator.comparing(
                file -> toJarInfo(file, customFileOrderMap),
                Comparator.comparing(JarInfo::getCategory)
                        .thenComparing(JarInfo::getOrder)
                        .thenComparing(JarInfo::getFile))));
    }

    enum Category {
        sdk,
        custom,
        classes,
        antx,
        lib,
        other
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    static class JarInfo {
        private Category category;
        private String file;
        private int order;
    }

    JarInfo toJarInfo(String file, Map<String, Integer> customFileOrderMap) {

        if (file.startsWith("/lib/") && file.matches(".*bundle-sdk.*\\.jar")) {
            return JarInfo.builder().category(Category.sdk).file(file).build();
        }
        Integer customOrder = customFileOrderMap.get(file);
        if (customOrder != null) {
            return JarInfo.builder()
                    .category(Category.custom)
                    .file(file)
                    .order(customOrder)
                    .build();
        }
        if (file.startsWith("/lib/")) {
            return JarInfo.builder().category(Category.lib).file(file).build();
        }
        if (file.startsWith("/classes/")) {
            return JarInfo.builder().category(Category.classes).file(file).build();
        }
        if (file.startsWith("/antx/")) {
            return JarInfo.builder().category(Category.antx).file(file).build();
        }
        return JarInfo.builder().category(Category.other).file(file).build();
    }
}
