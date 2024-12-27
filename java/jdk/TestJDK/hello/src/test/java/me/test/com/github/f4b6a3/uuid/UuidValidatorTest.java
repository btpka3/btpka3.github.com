package me.test.com.github.f4b6a3.uuid;

import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/27
 */
public class UuidValidatorTest {

    @Test
    public void test01() {
        System.out.println(UuidCreator.getTimeBased().toString());
        System.out.println(UuidCreator.getNameBasedSha1("zhang3").toString());
        System.out.println(UuidCreator.getNameBasedMd5("zhang3").toString());
        // ⭕️
        System.out.println(UuidCreator.getRandomBasedFast().toString());
    }
}
