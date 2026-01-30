package me.test.org.apache.commons.rng;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;
import org.apache.commons.rng.sampling.distribution.DiscreteSampler;
import org.apache.commons.rng.sampling.distribution.GaussianSampler;
import org.apache.commons.rng.sampling.distribution.RejectionInversionZipfSampler;
import org.apache.commons.rng.sampling.distribution.ZigguratSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class RandomSourceTest {

    /**
     * 随机生成
     */
    @Test
    public void test01() {
        UniformRandomProvider rng = RandomSource.XO_RO_SHI_RO_128_PP.create();
        boolean isOn = rng.nextBoolean();
        int min = 0;
        int max = 100;
        int n = rng.nextInt();         // Integer.MIN_VALUE <= n <= Integer.MAX_VALUE.
        int m = rng.nextInt(max);      // 0 <= m < max.
        int l = rng.nextInt(min, max); // min <= l < max.
        RandomSource.createInt();
    }

    /**
     * 特定分布的随机数 比如高斯分布，正态分布
     */
    @Test
    public void test02() {
        ContinuousSampler sampler = GaussianSampler.of(
                ZigguratSampler.NormalizedGaussian.of(RandomSource.ISAAC.create()),
                45.6,
                2.3
        );
        double random = sampler.sample();
        System.out.println(random);
    }

    @Test
    public void test03() {
        DiscreteSampler sampler = RejectionInversionZipfSampler.of(
                RandomSource.ISAAC.create(),
                5,
                1.2
        );
        int random = sampler.sample();
        System.out.println(random);
    }
}
