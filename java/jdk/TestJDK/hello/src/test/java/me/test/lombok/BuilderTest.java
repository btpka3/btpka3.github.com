package me.test.lombok;

import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/3/12
 */
public class BuilderTest {

    @Test
    public void test01() {
        Aaa aaa = Aaa.builder()
                .name("zhang3")
                .address("hangzhou")
                .build();
        System.out.println(aaa);

        Aaa aaa2 = aaa.toBuilder()
                .name("li4")
                .build();
        System.out.println(aaa2);

        Assertions.assertEquals("zhang3", aaa.getName());
        Assertions.assertEquals("li4", aaa2.getName());
    }

    @Data
    @Builder(toBuilder = true)
    public static class Aaa {
        @Builder.Default
        private long created = System.currentTimeMillis();
        private String name;
        private String address;
    }

}
