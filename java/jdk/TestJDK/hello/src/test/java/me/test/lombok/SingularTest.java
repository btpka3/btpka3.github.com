package me.test.lombok;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * @author dangqian.zll
 * @date 2024/3/12
 */
public class SingularTest {

    @Test
    public void test01() {
        Aaa aaa = Aaa.builder()
                .name("zhang3")
                .add("basketball")
                .add("football")
                .build();
        System.out.println(aaa);

        Aaa aaa2 = aaa.toBuilder()
                .name("li4")
                .add("tennis")
                .build();
        System.out.println(aaa2);

        Assertions.assertEquals("zhang3", aaa.getName());
        Assertions.assertEquals("li4", aaa2.getName());
    }

    @Data
    @Builder(toBuilder = true)
    public static class Aaa {
        private String name;

        @Singular("add")
        private List<String> hobbies;
    }
}
