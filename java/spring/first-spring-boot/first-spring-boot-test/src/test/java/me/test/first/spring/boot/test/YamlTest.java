package me.test.first.spring.boot.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.representer.Representer;

import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * @author dangqian.zll
 * @date 2023/6/21
 * @see <a href="https://yaml.org/spec/1.1/current.html">YAML 1.1</a>
 */
public class YamlTest {

    @SneakyThrows
    @Test
    public void test01() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        String document = IOUtils.toString(
                YamlTest.class.getResourceAsStream("yaml-001.yaml"),
                StandardCharsets.UTF_8
        );

        Map map = yaml.load(document);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
/* 输出:
{
  "key101" : "aa bb   \ncc dd   \n\nff  gg   ",
  "key102" : "aa bb   \ncc dd   \n\nff  gg   \n\n\n",
  "key103" : "aa bb   \ncc dd   \n\nff  gg   \n",
  "key201" : "aa bb    cc dd   \nff  gg   ",
  "key202" : "aa bb    cc dd   \nff  gg   \n\n\n",
  "key203" : "aa bb    cc dd   \nff  gg   \n",
  "key303" : " aa bb cc dd\nff  gg\n",
  "demo" : "demo"
}
 */
    }


    @Test
    public void testCollections1() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        Object o = yaml.load("- a\n- b # comment\n- [aaa,  bbb,ccc]");
        Assertions.assertInstanceOf(List.class, o);
        List list = (List) o;
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
        Assertions.assertEquals(Arrays.asList("aaa", "bbb", "ccc"), list.get(2));
    }

    @Test
    public void testMap() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        Object o = yaml.load("a: 111\nb1:\n  b2: bbb");
        Assertions.assertInstanceOf(Map.class, o);
        Assertions.assertInstanceOf(LinkedHashMap.class, o);
        Map map = (Map) o;
        Assertions.assertEquals(2, map.size());
        // 注意：类型是 Integer
        Assertions.assertEquals(111, map.get("a"));
        Object b1Obj = map.get("b1");
        Assertions.assertNotNull(b1Obj);
        Assertions.assertInstanceOf(Map.class, b1Obj);
        Map b1Map = (Map) b1Obj;
        Assertions.assertEquals(1, b1Map.size());
        Assertions.assertEquals("bbb", b1Map.get("b2"));
    }

    @Test
    public void testStructures() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        // 通过 `---` 切分多个文档
        // 通过 `...` 表示文档结束
        Iterable<Object> it = yaml.loadAll("---\na: aaa\nb: bbb\n...\n---\nc: ccc\nd: ddd\n...");
        List list = new ArrayList(2);
        it.forEach(list::add);

        Assertions.assertEquals(2, list.size());
        {
            Object o = list.get(0);
            Assertions.assertNotNull(o);
            Assertions.assertInstanceOf(Map.class, o);
            Map m = (Map) o;
            Assertions.assertEquals("aaa", m.get("a"));
            Assertions.assertEquals("bbb", m.get("b"));
        }
        {
            Object o = list.get(1);
            Assertions.assertNotNull(o);
            Assertions.assertInstanceOf(Map.class, o);
            Map m = (Map) o;
            Assertions.assertEquals("ccc", m.get("c"));
            Assertions.assertEquals("ddd", m.get("d"));
        }
    }

    @Test
    public void testInteger() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        Object o = yaml.load("canonical: 12345\n" +
                "decimal: +12345\n" +
                "octal: 0o14\n" +
                "hexadecimal: 0xC"
        );
        Assertions.assertInstanceOf(Map.class, o);
        Map map = (Map) o;
        Assertions.assertEquals(4, map.size());
        // 注意：类型是 Integer
        Assertions.assertEquals(12345, map.get("canonical"));
        Assertions.assertEquals(12345, map.get("decimal"));
        // FIXME 不支持 8进制
        Assertions.assertEquals("0o14", map.get("octal"));
        Assertions.assertEquals(12, map.get("hexadecimal"));
    }

    /**
     * 先使用 `&xxxAnchor` 定义锚点，再使用 `*xxxAnchor` 进行引用
     */
    @Test
    public void testReference() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        Map map = (Map) yaml.load(
                "" +
                        "a:\n" +
                        "  - xxx\n" +
                        "  - &SS aaa\n" +
                        "b:\n" +
                        "  - yyy\n" +
                        "  - *SS\n"
        );
        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals(Arrays.asList("xxx", "aaa"), map.get("a"));
        Assertions.assertEquals(Arrays.asList("yyy", "aaa"), map.get("b"));
    }

    /**
     * @see YamlPropertiesFactoryBean
     */
    @Test
    public void fromProperties() {

        Map<String, String> map = new HashMap<>(128);
        map.put("a", "aaa");
        map.put("b[1]", "b1");
        map.put("b[2]", "b2");

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);

        Yaml yaml = new Yaml(dumperOptions);
        String str = yaml.dump(map);
        System.out.println(str);
    }

    @Test
    public void toProperties() {
        String str = "" +
                "a: aaa\n" +
                "b:\n" +
                "  - b1\n" +
                "  - b2\n" +
                "c: >-\n" +
                "  c1 c2\n" +
                "  c3 c4\n";
        Yaml yaml = new Yaml();
        Map map = yaml.load(str);
        System.out.println(map.getClass());
        System.out.println(map);
    }


    @Test
    public void testFlattenMap1() {
        Map map = new LinkedHashMap(8);
        map.put("a", "aaa");
        map.put("b", Arrays.asList("b1", "b2"));
        //Map result = flattern(map);
        Map result = flattern2(map);
        System.out.println(result);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("aaa", result.get("a"));
        Assertions.assertEquals("b1", result.get("b[0]"));
        Assertions.assertEquals("b2", result.get("b[1]"));
    }

    @Test
    public void testFlatternMap2() {
        Map m1 = new LinkedHashMap();
        m1.put("m1_k1", "m1_v1");
        m1.put("m1_k2", "m1_v2");

        Map m2 = new LinkedHashMap();
        m2.put("m2_k1", "m2_v1");
        m2.put("m2_k2", "m2_v2");

        Map map = new LinkedHashMap();
        map.put("a", "aaa");
        map.put("b", Arrays.asList(m1, m2));
        //Map result = flattern(map);
        Map result = flattern2(map);
        System.out.println(result);
        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals("aaa", result.get("a"));
        Assertions.assertEquals("m1_v1", result.get("b[0].m1_k1"));
        Assertions.assertEquals("m1_v2", result.get("b[0].m1_k2"));
        Assertions.assertEquals("m2_v1", result.get("b[1].m2_k1"));
        Assertions.assertEquals("m2_v2", result.get("b[1].m2_k2"));
    }

    protected Map flattern2(Map source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    /**
     * @param result
     * @param source
     * @param path
     * @see YamlPropertiesFactoryBean#buildFlattenedMap
     */
    protected void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, @Nullable String path) {
        source.forEach((key, value) -> {
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Collection<Object> collection = (Collection<Object>) value;
                if (collection.isEmpty()) {
                    result.put(key, "");
                } else {
                    int count = 0;
                    for (Object object : collection) {
                        buildFlattenedMap(result, Collections.singletonMap(
                                "[" + (count++) + "]", object), key);
                    }
                }
            } else {
                result.put(key, (value != null ? value : ""));
            }
        });
    }

    /**
     * 将yaml加载的层级map转换成properties，类似于 {@link YamlPropertiesFactoryBean}
     *
     * @param map
     * @return
     * @see YamlPropertiesFactoryBean#createProperties
     * @see YamlPropertiesFactoryBean#buildFlattenedMap
     */
    protected Map flattern(Map map) {
        Map result = new LinkedHashMap(map.size() * 2);

        map.forEach((k, v) -> {
            handle(result, (String) k, v);
        });
        return result;
    }

    protected void handle(Map result, String k, Object v) {
        if (v instanceof Map) {
            result.putAll(genMap((String) k, (Map) v));
        } else if (v instanceof List) {
            result.putAll(genList((String) k, (List) v));
        } else {
            result.put(k, v);
        }
    }

    protected Map genMap(String key, Map map) {
        Map result = new LinkedHashMap(map.size() * 2);
        map.forEach((k, v) -> {
            String newKey = key + "." + k;
            if (v instanceof Map) {
                result.putAll(genMap(newKey, (Map) v));
            } else if (v instanceof List) {
                result.putAll(genList(newKey, (List) v));
            } else {
                result.put(newKey, v);
            }
        });
        return result;
    }

    protected Map genList(String key, List list) {
        Map result = new LinkedHashMap(list.size() * 2);
        for (int i = 0; i < list.size(); i++) {
            Object v = list.get(i);
            String newKey = key + "[" + i + "]";
            if (v instanceof Map) {
                result.putAll(genMap(newKey, (Map) v));
            } else if (v instanceof List) {
                result.putAll(genList(newKey, (List) v));
            } else {
                result.put(newKey, v);
            }
        }
        return result;
    }

    public static Object sortMaps(Object obj) {
        if (obj instanceof Map) {
            if(obj instanceof TreeMap<?,?>){
                return obj;
            }
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<Object, Object> sorted = new TreeMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                sorted.put(entry.getKey(), sortMaps(entry.getValue()));
            }
            return sorted;
        } else if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            List<Object> sorted = new ArrayList<>();
            for (Object item : list) {
                sorted.add(sortMaps(item));
            }
            return sorted;
        }
        return obj;
    }

    @Test
    public void testSorted() {
        // language=yaml
        String str = """
                bbb: b1 # comment1
                ccc: c1
                aaa: a1
                # comment2
                a:
                  bbb:  # comment3
                    - b1
                    - b23: b2333
                      b21: b2111
                      b22: b2222
                    - b3
                  ccc:
                    c3: c333
                    c1: c111
                    c2: c222
                  aaa: a2张3
                d: |
                  aa bb  \s
                  cc dd  \s
                   
                  ff  gg  \s
                """;

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setProcessComments(true);
        Constructor c = new Constructor(loaderOptions);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);


        // 对于POJO 的key排序输出，可以使用 自定义Representer
        Representer representer = new Representer(dumperOptions) {
            @Override
            protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
                // 对属性按名称排序
                List<Property> sortedProps = new ArrayList<>(properties);
                sortedProps.sort(Comparator.comparing(Property::getName));
                return super.representJavaBean(new LinkedHashSet<>(sortedProps), javaBean);
            }
        };
        //Yaml yaml = new Yaml(c, representer, dumperOptions);
        Yaml yaml = new Yaml(loaderOptions, dumperOptions);
        Map map = yaml.load(str);

        // ====================== 换行符前没有空白
        // 最后有换行符
        map.put("x11", "line1\nline2\n");
        // 最后没有换行符
        map.put("x12", "line1\nline2");

        // ====================== 换行符前有空白
        // 最后有换行符(换行符前有空白）
        map.put("x21", "line1 \nline2 \n");
        // 最后没有换行符(换行符前没有空白）
        map.put("x22", "line1 \nline2 ");
        // 对于map 的key排序输出，则可以先转换成treeMap
        map= (Map) sortMaps(map);

        String result = yaml.dump(map);
        System.out.println("======================");
        System.out.println(result);
    }
}
