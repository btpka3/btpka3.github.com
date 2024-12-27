package me.test.com.fasterxml.uuid;

import com.fasterxml.uuid.Generators;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/27
 */
public class GeneratorsTest {
    @Test
    public void timeBasedEpochRandomGenerator01() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Generators.timeBasedEpochRandomGenerator().generate());
        }
    }
}
