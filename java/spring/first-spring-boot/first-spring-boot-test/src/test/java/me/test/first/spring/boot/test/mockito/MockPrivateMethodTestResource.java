package me.test.first.spring.boot.test.mockito;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
public class MockPrivateMethodTestResource {
    public static class Calculator {
        private int add(int a, int b) {
            return a + b;
        }

        public int sum(int a, int b) {
            return add(a, b);
        }
    }
}
