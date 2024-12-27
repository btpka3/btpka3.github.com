package me.test.jdk.javax.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import lombok.val;

import java.util.ArrayList;

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
