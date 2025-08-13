package me.test.jdk.java.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class BigDecimalTest {

    public static void main(String[] args) throws InterruptedException {
        String numStr = null;
        BigDecimal num = null;

        // 精密度：数值中数字的个数，有效数字位数（从第一个非零数字开始，直到末尾，不含小数点）
        int precision;

        // 刻度、数值范围、小数点后数字位数
        int scale;

        // 4,0
        numStr = "8000";
        num = new BigDecimal(numStr);
        scale = num.scale();
        precision = num.precision();
        System.out.printf("'%s' : (%d,%d) \n", numStr, precision, scale);

        // 7,5
        numStr = "10.00010";
        num = new BigDecimal(numStr);
        scale = num.scale();
        precision = num.precision();
        System.out.printf("'%s' : (%d,%d) \n", numStr, precision, scale);

        // 2,5
        numStr = "000000000.00010";
        num = new BigDecimal(numStr);
        scale = num.scale();
        precision = num.precision();
        System.out.printf("'%s' : (%d,%d) \n", numStr, precision, scale);

    }

    Double d = 123456789.123456789;
    int loop = 10000000;

    public void testValueOfDouble() {
        AtomicReference<BigDecimal> ref = new AtomicReference<>();
        long start = System.nanoTime();
        for (int i = 0; i < loop; i++) {
            BigDecimal b = BigDecimal.valueOf(d);
            ref.set(b);
        }
        long end = System.nanoTime();
        long cost = end - start;
        Assertions.assertEquals(d, ref.get().doubleValue());
        System.out.printf("%30s : loop=%8d, cost=%12d %n", "testValueOfDouble", loop, cost);
    }

    public void testConstructorWithDouble() {
        AtomicReference<BigDecimal> ref = new AtomicReference<>();
        long start = System.nanoTime();
        for (int i = 0; i < loop; i++) {
            BigDecimal b = new BigDecimal(d);
            ref.set(b);
        }
        long end = System.nanoTime();
        long cost = end - start;
        Assertions.assertEquals(d, ref.get().doubleValue());
        System.out.printf("%30s : loop=%8d, cost=%12d %n", "testConstructorWithDouble", loop, cost);
    }

    @Test
    public void testValueOfDouble_Vs_ConstructorWithDouble() {
        testValueOfDouble();
        testConstructorWithDouble();
    }

}
