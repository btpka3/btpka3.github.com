package me.test.jdk.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2024/8/7
 */
public class ArrayListTest {

    @Test
    public void test01() {
        try {
            List<String> list = new ArrayList<>();
            list.set(0, "aaa");
            Assertions.fail();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() {
        List<String> list = new ArrayList<>(3);
        // 仍然不行因为其size仍为0
        try {
            list.set(0, "aaa");
            Assertions.fail();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
