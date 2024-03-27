package me.test.lombok;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/3/12
 */
public class SuperBuilderTest {

    @Test
    public void test01() {
        Aaa aaa = Aaa.builder()
                .name("zhang3")
                .address("hangzhou")
                .build();
        System.out.println(aaa);

        Bbb bbb = Bbb.builder()
                .name("name01")
                .age(12)
                .hobbies("hobbies01")
                .address("address01")
                .build();
        System.out.println(bbb);
    }

    @Data
    @SuperBuilder(toBuilder = true)
    public static class Aaa {
        private String name;
        private String address;
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @ToString(callSuper = true)
    public static class Bbb extends Aaa {
        private Integer age;
        private String hobbies;
    }
}
