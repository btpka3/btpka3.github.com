package me.test.jdk.java.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * UUID#randomUUID() 内部使用全局的 SecureRandom ， 高并发时会有性能问题。
 * 比如，使用 DRBG 算法 的 SecureRandom 时，用到的方法
 * sun.security.provider.AbstractDrbg#generateAlgorithm(byte[], byte[]) 方法都是 synchronized
 *
 * @author dangqian.zll
 * @date 2023/12/13
 * @see <a href="https://github.com/btpka3/btpka3.github.com/blob/c2ca13189086ffefc22f1ee5bd7efbaac596d866/java/first-jvm-sandbox/src/main/java/io/github/btpka3/first/jvm/sandbox/advice/UUIDAdvice.java#L83">UUIDAdvice</a>
 */
public class UUIDTest {

    /**
     *
     */
    @Test
    public void test01() {
        UUID.randomUUID();
    }

    @SneakyThrows
    public void test02() {
        SecureRandom secureRandom = SecureRandom.getInstance("DRBG");

        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
    }

}
