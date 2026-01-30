package me.test.jdk.javax.annotation;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/4
 */
public class NonnullTest {

    @Test
    public void testNonnull() {
        try {
            val example = new ArrayList<String>();
            MyPerson p = new MyPerson();
            p.setName(null);
            Assertions.fail("should throw NPE");
        } catch (NullPointerException e) {

        }
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPerson {
        @Nonnull
        private String name;
        private Integer age;
    }

}
