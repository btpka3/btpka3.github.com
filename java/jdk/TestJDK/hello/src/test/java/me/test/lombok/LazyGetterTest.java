package me.test.lombok;

import java.util.Arrays;
import lombok.Getter;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/4
 */
public class LazyGetterTest {

    @Test
    public void test01() {
        GetterLazyExample e = new GetterLazyExample();
        System.out.println(Arrays.toString(e.getCached()));
    }

    public class GetterLazyExample {
        @Getter(lazy = true)
        private final double[] cached = expensive();

        private double[] expensive() {
            double[] result = new double[10];
            for (int i = 0; i < result.length; i++) {
                result[i] = Math.asin(i);
            }
            return result;
        }

        // 这个方法将报错。
//        protected int length() {
//            return cached.length;
//        }
    }
}
