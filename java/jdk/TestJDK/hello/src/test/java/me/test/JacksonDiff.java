package me.test;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dangqian.zll
 * @date 2024/7/18
 */
public class JacksonDiff {

    @Test
    @SneakyThrows
    public void diff01() {
        ObjectMapper mapper = new ObjectMapper();
        // language=JSON
        String jsonStr1 = """
                {
                    "a":"{\\\"x\\\":\\\"xx\\\",\\\"y\\\":\\\"yy\\\"}"
                }
                """;
        // language=JSON
        String jsonStr2 = """
                {
                    "a":"{\\\"y\\\":\\\"yy\\\",\\\"x\\\":\\\"xx\\\"}"
                }
                """;

        JsonNode actualObj1 = mapper.readTree(jsonStr1);
        JsonNode actualObj2 = mapper.readTree(jsonStr2);
        assertNotEquals(actualObj1, actualObj2);
        Comparator<JsonNode> cmp = (n1, n2) -> {
            try {
                Map map1 = mapper.readValue(n1.asText(), Map.class);
                Map map2 = mapper.readValue(n2.asText(), Map.class);
                return Objects.equals(map1, map2) ? 0 : -1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        };
        assertTrue(actualObj1.equals(cmp, actualObj2));
    }


    @SneakyThrows
    @Test
    public void test() {
        String publishInfoJson = null;
        {
            String fileName = "_uf_app_publish_item_publish_info_1721291516088.txt";
//            String fileName = "_uf_app_publish_item_publish_info_1721293094431.txt";
            File file = new File(System.getProperty("user.home") + "/Downloads/" + fileName);
            publishInfoJson = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
        }
        String draftInfoFJson = null;
        {
            String fileName = "_uf_app_publish_item_draft_info_1721291667117.txt";
//            String fileName = "_uf_app_publish_item_draft_info_1721293123771.txt";
            File file = new File(System.getProperty("user.home") + "/Downloads/" + fileName);
            draftInfoFJson = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
        }

        Map publishMap = JSON.parseObject(publishInfoJson);
        Map draftMap = JSON.parseObject(draftInfoFJson);

        Map appPublishInfoMap1 = MapUtils.getMap(publishMap, "appPublishInfoMap");
        Map appPublishInfoMap2 = MapUtils.getMap(draftMap, "appPublishInfoMap");


        Set<String> clusterNames = appPublishInfoMap1.keySet();
        for (String clusterName : clusterNames) {
            Map clusterConfig1 = MapUtils.getMap(appPublishInfoMap1, clusterName);
            Map clusterConfig2 = MapUtils.getMap(appPublishInfoMap2, clusterName);

            List appAntxProperty1 = (List) MapUtils.getObject(clusterConfig1, "appAntxProperty");
            List appAntxProperty2 = (List) MapUtils.getObject(clusterConfig2, "appAntxProperty");

            Map appAntxPropertyMap1 = toMap(appAntxProperty1);
            Map appAntxPropertyMap2 = toMap(appAntxProperty2);
            try {
                MapDiff mapDiff = diffMap(appAntxPropertyMap1, appAntxPropertyMap2);
                assertFalse(mapDiff.hasDiff(), "clusterName:" + clusterName + " appAntxProperty : mapDiff=" + mapDiff);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        for (String clusterName : clusterNames) {
            Map clusterConfig1 = MapUtils.getMap(appPublishInfoMap1, clusterName);
            Map clusterConfig2 = MapUtils.getMap(appPublishInfoMap2, clusterName);

            String version1 = MapUtils.getString(clusterConfig1, "version");
            String version2 = MapUtils.getString(clusterConfig2, "version");
            assertNotEquals(version1, version2, "clusterName:" + clusterName + " version not equals");
        }
    }

    protected Map toMap(List list) {
        if (list == null) {
            return Collections.emptyMap();
        }
        Map map = new HashMap(32);
        for (Object o : list) {
            Map m = (Map) o;
            String key = MapUtils.getString(m, "name");
            String value = MapUtils.getString(m, "value");
            map.put(key, value);
        }
        return map;
    }

    protected MapDiff diffMap(Map map1, Map map2) {
        Set<String> keySet1 = map1.keySet();
        Set<String> keySet2 = map2.keySet();

        Set<String> keyOnlyIn1 = SetUtils.difference(keySet1, keySet2);
        Set<String> keyOnlyIn2 = SetUtils.difference(keySet2, keySet1);

        Set<String> commonKeys = SetUtils.intersection(keySet1, keySet2);

        List<MapDiffValue> diffValues = new ArrayList<>(16);
        for (String key : commonKeys) {
            String v1 = (String) map1.get(key);
            String v2 = (String) map1.get(key);
            if (Objects.equals(v1, v2)) {
                continue;
            }
            MapDiffValue diffValue = MapDiffValue.builder()
                    .key(key)
                    .value1(v1)
                    .value2(v2)
                    .build();
            diffValues.add(diffValue);
        }
        return MapDiff.builder()
                .keyOnlyIn1(keyOnlyIn1)
                .keyOnlyIn2(keyOnlyIn2)
                .diffValues(diffValues)
                .build();
    }

    @Data
    @Builder(toBuilder = true)
    public static class MapDiffValue {
        String key;
        String value1;
        String value2;
    }

    @Data
    @Builder(toBuilder = true)
    public static class MapDiff {
        Set<String> keyOnlyIn1;
        Set<String> keyOnlyIn2;
        List<MapDiffValue> diffValues;

        public boolean hasDiff() {
            return !keyOnlyIn1.isEmpty() || !keyOnlyIn2.isEmpty() || !diffValues.isEmpty();
        }
    }


}
