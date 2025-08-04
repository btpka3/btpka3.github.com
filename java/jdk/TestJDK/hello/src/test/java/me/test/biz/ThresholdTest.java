package me.test.biz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/8/4
 */
public class ThresholdTest {
    static final int MAX_THRESHOLD = 10000;

    /**
     * 判断是否在某个阈值范围内，通常用于按比例处理业务逻辑。
     * <p>
     * 使用示例 :
     * <pre>{@code
     *  Map<String, Integer> xxxConfigMap = ...;
     *  if (isInThreshold(10000, xxxConfigMap.get("xxx"), (int)System.nanoTime)) {
     *       // do something
     *  }
     * }</pre>
     * <p>
     * 阈值配置范围 = [0, ${maxThreshold}]。
     *
     * @param maxThreshold  最大阈值
     * @param configPercent 当前的配置值。
     * @param src           此次计算的因子，
     */
    public static boolean isInThreshold(int maxThreshold, int configPercent, int src) {
        if (configPercent <= 0) {
            return false;
        }
        if (configPercent >= maxThreshold) {
            return true;
        }
        return (Math.abs(src) % maxThreshold + 1) <= configPercent;
    }

    protected void demo1() {
        Map<String, Integer> xxxConfigMap = new HashMap<>(16);
        xxxConfigMap.put("xxx", 500);

        if (isInThreshold(MAX_THRESHOLD, xxxConfigMap.get("xxx"), (int) System.nanoTime())) {
            // do something
        }
    }

    protected void demo2() {
        Map<String, Integer> xxxConfigMap = new HashMap<>(16);
        xxxConfigMap.put("xxx", 500);
        Object xxxObject = new Object();
        if (isInThreshold(MAX_THRESHOLD, xxxConfigMap.get("xxx"), xxxObject.hashCode())) {
            // do something
        }
    }

    @Test
    public void test01() {
        long nanoTime = System.nanoTime();
        int theInt = (int) nanoTime;
        System.out.println("nanoTime=" + nanoTime + ", theInt=" + theInt);

        Object[][] arr = new Object[][]{
                // percent, src, expectResult
                new Object[]{0, -1, false},
                new Object[]{0, 0, false},
                new Object[]{0, 1, false},
                new Object[]{500, -1, true},
                new Object[]{500, 0, true},
                new Object[]{500, 1, true},
                new Object[]{500, 499, true},
                new Object[]{500, 500, false},
                new Object[]{500, 501, false},
                new Object[]{10000, -1, true},
                new Object[]{10000, 0, true},
                new Object[]{10000, 1, true},
                new Object[]{10000, 9999, true},
                new Object[]{10000, 10000, true},
                new Object[]{10000, 10001, true},
        };

        for (Object[] subArr : arr) {
            int curConfig = (int) subArr[0];
            int curSrc = (int) subArr[1];
            boolean expectedResult = (boolean) subArr[2];
            Assertions.assertEquals(
                    expectedResult,
                    isInThreshold(MAX_THRESHOLD, curConfig, curSrc),
                    "curConfig: " + curConfig + ", curSrc: " + curSrc + ", expectedResult: " + expectedResult
            );
        }
    }
}
