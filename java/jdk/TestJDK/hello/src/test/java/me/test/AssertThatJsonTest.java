package me.test;

import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * @author dangqian.zll
 * @date 2024/6/21
 */
public class AssertThatJsonTest {

    @Test
    public void test01() {

        String jsonStr1 = """
                {"test":[1,2,3],"a":[2]}
                """;
        String jsonStr2 = """
                {"a":[2],"test":[3,1, 2]}
                """;

        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(jsonStr2);
    }


    @Test
    public void test02() {

        String jsonStr1 = """
                {"test":[1,2,3],"a":[2]}
                """;
        String jsonStr2 = """
                {"test":[3,1, 2],"a":[2,3]}
                """;

        assertThatJson(jsonStr1)
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(jsonStr2);
    }


    @Test
    public void test03() {

        String jsonStr1 = """
                {
                    "test":[1,2,3],
                    "a":{
                        "b":"b01"
                    },
                     "c":"c01"
                }
                """;
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
}
