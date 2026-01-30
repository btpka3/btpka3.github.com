package me.test;

import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * @author dangqian.zll
 * @date 2024/6/21
 * @see net.javacrumbs.jsonunit.core.internal.Diff
 * @see net.javacrumbs.jsonunit.core.internal.Diff#compareValues
 */
public class AssertThatJsonTest {

    @Test
    public void test01() {

        // language=JSON
        String jsonStr1 = """
                {"test":[1,2,3],"a":[2]}
                """;
        // language=JSON
        String jsonStr2 = """
                {"a":[2],"test":[3,1, 2]}
                """;

        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(jsonStr2);
    }


    @Test
    public void test02() {
        // language=JSON
        String jsonStr1 = """
                {"test":[1,2,3],"a":[2]}
                """;
        // language=JSON
        String jsonStr2 = """
                {"test":[3,1, 2],"a":[2,3]}
                """;

        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(jsonStr2);
    }


    @Test
    public void test03() {

        // language=JSON
        String jsonStr1 = """
                {
                    "test":[1,2,3],
                    "a":{
                        "b":"b01"
                    },
                     "c":"c01"
                }
                """;
        // language=JSON
        String jsonStr2 = """
                {
                    "test":[1,2,3],
                    "a":{
                        "b":"b02"
                    },
                    "c":"c02"
                }
                """;

        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(jsonStr2);
    }

    /**
     * 内布值是字符串，字符串使用自定比较
     */
    @Test
    public void test04() {

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
        System.out.println(jsonStr1);

        // 不支持字段 自定义 comparator
        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isNotEqualTo(jsonStr2);

//        assertThatJson(jsonStr1)
//                .withConfiguration(c -> c.withNumberComparator(new DefaultNumberComparator()))
//                //.usingComparator()
//                .withMatcher("aaax", JsonMatchers.jsonPartMatches("$.a", JsonMatchers.jsonStringEquals("{\"y\":\"yy111\",\"x\":\"xx\"}")))
////                .when(path("$.a"),(c, pp)->{
////                    c.withMatcher("aaa",
////                        JsonMatchers.jsonPartMatches("$.a", JsonMatchers.isString()));
////                })
//                .isEqualTo(jsonStr2)
        ;
//                .inPath("$.a")
//                .isString()
//                .usingComparator((o1, o2) -> {
//                    Diff diff = Diff.create(o2, o1, "fullJson", "$", Configuration.empty());
//                    return diff.similar() ? 0 : 1;
//                })
//                .isEqualTo(jsonStr2);
    }
}
