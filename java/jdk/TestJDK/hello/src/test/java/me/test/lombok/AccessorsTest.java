package me.test.lombok;

import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

/**
 * 测试 @Accessors 。
 * - @Accessors 只是用来配置如何生成 getter/setter， 故如果配置了 fluent = true, 将不会再有标准的getter、setter。
 * - 有父子继承关系时，最简便的使用方式是先试用子类的 fluent setter ，再用父类的 fluent setter， 最后 类型强转（cast）成子类。
 *
 * @author dangqian.zll
 * @date 2024/3/12
 * @see <a href="Using Lombok’s @Accessors Annotation">https://www.baeldung.com/lombok-accessors</a>
 * @see <a href="https://stackoverflow.com/q/36564153/533317">How can I generate both standard accessors and fluent accessors with lombok?</a>
 */
public class AccessorsTest {

    @Test
    public void test01() {
        Bbb bbb = (Bbb) new Bbb()
                .age(18)
                .hobbies("swimming")
                .name("zhang3")
                .address("beijing");
        System.out.println(bbb);
    }


    @Data
    @Accessors(fluent = true)
    public static class Aaa {
        private String name;
        private String address;
    }

    @Data
    @Accessors(fluent = true)
    public static class Bbb extends Aaa {
        private Integer age;
        private String hobbies;
    }


}
