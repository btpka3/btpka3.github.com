package me.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2023/9/28
 */
public class AssertJTest {

    /**
     * 检查报错的异常类型== java.lang.AssertionError， 故仅仅适用于单元测试，不适合业务正常逻辑。
     */
    @Test
    public void x() {
        assertThat("a").isEmpty();
    }
}
