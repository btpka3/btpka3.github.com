package me.test.org.apache.commons.text.similarity;

import org.apache.commons.text.similarity.HammingDistance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class HammingDistanceTest {

    @Test
    public void test01() {
        HammingDistance distance = new HammingDistance();
        Integer result = distance.apply("1011101", "1011111");
        Assertions.assertEquals(1, result);
    }
}
