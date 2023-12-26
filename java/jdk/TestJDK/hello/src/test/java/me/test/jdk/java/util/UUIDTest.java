package me.test.jdk.java.util;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * UUID#randomUUID() 内部使用全局的 SecureRandom ， 高并发时会有性能问题。
 * 比如，使用 DRBG 算法 的 SecureRandom 时，用到的方法
 * sun.security.provider.AbstractDrbg#generateAlgorithm(byte[], byte[]) 方法都是 synchronized
 *
 * @author dangqian.zll
 * @date 2023/12/13
 */
public class UUIDTest {

    /**
     *
     */
    @Test
    public void test01() {
        UUID.randomUUID();
    }
}
