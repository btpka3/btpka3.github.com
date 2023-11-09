package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author dangqian.zll
 * @date 2021/7/8
 */
@Disabled
public class FastJSONTest {


    /**
     * map1, map2 都引用 map3
     */
    @Test
    public void testCycleReference01() {
        Map root = new HashMap(4);

        {

            Map map1 = new HashMap(4);
            map1.put("a", "aaa");

            Map map2 = new HashMap(4);
            map2.put("b", "bbb");

            Map map3 = new HashMap(4);
            map3.put("c", "ccc");

            map1.put("map3", map3);
            map2.put("map3", map3);

            root.put("map1", map1);
            root.put("map2", map2);
        }

        String jsonStr = JSON.toJSONString(root,
            //SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.PrettyFormat
        );

        System.out.println("jsonStr=\n" + jsonStr);

        Map newRoot = JSON.parseObject(jsonStr);

        {
            Map map1 = (Map) newRoot.get("map1");
            Map map2 = (Map) newRoot.get("map2");

            assertEquals("aaa", map1.get("a"));
            assertEquals("bbb", map2.get("b"));

            Map map3 = (Map) map1.get("map3");
            assertEquals("ccc", map3.get("c"));
            // 启用 SerializerFeature.DisableCircularReferenceDetect 的话，这里将报错
            // 这里将是一个副本，作为缓存的话，不合适
            assertSame(map3, map2.get("map3"));
        }
    }

    /**
     * map1, map2 之间双向引用
     */
    @Test
    public void testCycleReference02() {
        Map root = new HashMap(4);

        {
            Map map1 = new HashMap(4);
            map1.put("a", "aaa");

            Map map2 = new HashMap(4);
            map2.put("b", "bbb");

            // map1, map2 之间双向引用
            map1.put("map2", map2);
            map2.put("map1", map1);

            // 添加到root
            root.put("map1", map1);
            root.put("map2", map2);
        }

        // 启用： SerializerFeature.DisableCircularReferenceDetect 的话， 将 StackOverflowError
        String jsonStr = JSON.toJSONString(root,
            //SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.PrettyFormat
        );

        System.out.println("jsonStr=\n" + jsonStr);

        Map newRoot = JSON.parseObject(jsonStr);

        {
            Map map1 = (Map) newRoot.get("map1");
            Map map2 = (Map) newRoot.get("map2");

            assertEquals("aaa", map1.get("a"));
            assertEquals("bbb", map2.get("b"));

            assertSame(map1, map2.get("map1"));
            // 不启用： SerializerFeature.DisableCircularReferenceDetect 的话， 这里将报错
            assertSame(map2, map1.get("map2"));
        }
    }
}
