package me.test.io.azam.ulidj;

import io.azam.ulidj.ULID;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/27
 */
public class ULIDTest {

    @Test
    @SneakyThrows
    public void test01() {
        String ulid1 = ULID.random();
        System.out.println(ulid1);
        String ulid2 = ULID.random(ThreadLocalRandom.current());
        System.out.println(ulid2);
        String ulid3 = ULID.random(SecureRandom.getInstance("SHA1PRNG"));
        System.out.println(ulid3);
        byte[] entropy = new byte[] {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9};
        String ulid4 = ULID.generate(System.currentTimeMillis(), entropy); // Generate ULID in string representation
        System.out.println(ulid4);
        byte[] ulid5 =
                ULID.generateBinary(System.currentTimeMillis(), entropy); // Generate ULID in binary representation
        System.out.println(ulid5);
    }
}
