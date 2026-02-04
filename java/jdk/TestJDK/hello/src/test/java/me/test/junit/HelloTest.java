package me.test.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/16
 */
public class HelloTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("============ HelloTest#beforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("============ HelloTest#beforeEach");
    }

    @Test
    public void test01() {}

    @Test
    public void test02() {
        throw new RuntimeException("Demo Error");
    }
}
