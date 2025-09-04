package me.test.jdk.java.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

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

    @Test
    public void testVariousFormat() {
        double d1 = 0.1D;
        Double d2 = d1;
        BigDecimal d3 = new BigDecimal(d2);
        BigDecimal d4 = new BigDecimal(d1);
        System.out.println("d1=" + d1);
        System.out.println("d2=" + d2);
        System.out.println("d3=" + d3);
        System.out.println("d4=" + d4);
        System.out.println("d4.doubleValue()==" + d1 + " : " + (d4.doubleValue() == d1));
        System.out.println("Double.toString(0.1D)    = " + Double.toString(0.1D));
        System.out.println("Double.toString(0.1F)    = " + Double.toString(0.1F));
        System.out.println("Float.toString(0.1F)     = " + Float.toString(0.1F));
        System.out.println("BigDecimal.valueOf(0.1D) = " + BigDecimal.valueOf(0.1D));
        System.out.println("BigDecimal.valueOf(0.1F) = " + BigDecimal.valueOf(0.1F));
        System.out.println("new BigDecimal(0.1D)     = " + new BigDecimal(0.1D));
        System.out.println("new BigDecimal(0.1F)     = " + new BigDecimal(0.1F));


        Assertions.assertNotEquals(BigDecimal.valueOf(0.1D), BigDecimal.valueOf(0.1F));
        Assertions.assertNotEquals(BigDecimal.valueOf(0.1D).doubleValue(), BigDecimal.valueOf(0.1F).doubleValue());
        Assertions.assertNotEquals(new BigDecimal(0.1D), new BigDecimal(0.1F));
        Assertions.assertNotEquals(new BigDecimal(0.1D).doubleValue(), new BigDecimal(0.1F).doubleValue());
        Assertions.assertEquals(0.1D, new BigDecimal(0.1D).doubleValue());
        Assertions.assertEquals(0.1f, new BigDecimal(0.1f).doubleValue());
        Assertions.assertEquals(123456789.123456789, new BigDecimal("123456789.123456789").doubleValue());
        // 有前导0
        Assertions.assertEquals(123456789.123456789, new BigDecimal("00123456789.123456789").doubleValue());
        // 有后导0
        Assertions.assertEquals(123456789.123456789, new BigDecimal("123456789.12345678900").doubleValue());
        // 科学计数法法(小写e)
        Assertions.assertEquals(0.01, new BigDecimal("1e-2").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("1.0e-2").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("0.1e-1").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("100e-4").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("100e-04").doubleValue());
        // 科学计数法法(大写E)
        Assertions.assertEquals(0.01, new BigDecimal("1E-2").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("1.0E-2").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("0.1E-1").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("100E-4").doubleValue());
        Assertions.assertEquals(0.01, new BigDecimal("100E-04").doubleValue());

        // 不支持系列
        // java double 表达法
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0.1d"));
        // java float 表达法
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0.1F"));
        // java long 表达法
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("1L"));
        // 十六进制表达法
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0xFF"));
        // Underscores in Numeric Literals
        // https://docs.oracle.com/javase/7/docs/technotes/guides/language/underscores-literals.html
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("1234_5678_9012_3456L"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("999_99_9999L"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("3.14_15F"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0xFF_EC_DE_5E"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0xCAFE_BABE"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0x7fff_ffff_ffff_ffffL"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0b0010_0101"));
        Assertions.assertThrows(NumberFormatException.class, () -> new BigDecimal("0b11010010_01101001_10010100_10010010"));
    }

    @Test
    public void testEquals() {
        // 封装类型的 equals 有类型判断
        Assertions.assertFalse(Objects.equals(0.1D, 0.1F));

        BiFunction<Number, Number, Integer> compareTo = (Number n1, Number n2) -> {
            BigDecimal b1 = number2BigDecimal(n1);
            BigDecimal b2 = number2BigDecimal(n2);
            return b1.compareTo(b2);
        };
        Assertions.assertEquals(0, compareTo.apply(0.1D, 0.1F));
    }

    @Test
    public void testCast() {
        Float f = 0.1f;
        double d = (double) f;
        System.out.println("f=" + f);
    }

    /**
     * 推荐
     *
     * @param n
     * @return
     */
    public BigDecimal number2BigDecimal(Number n) {
        return new BigDecimal(n.toString());
    }


    /**
     * 不推荐，枚举不完，且 double,float 直接转成 BigDecimal 会因精度问题不可预测。
     *
     * @param n
     * @return
     */
    @Deprecated
    public BigDecimal number2BigDecimal2(Number n) {
        if (n == null) {
            return null;
        }
        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        }
        if (n instanceof BigInteger) {
            return new BigDecimal((BigInteger) n);
        }
        if (n instanceof Integer
                || n instanceof Short
                || n instanceof Byte
        ) {
            return new BigDecimal((int) n);
        }
        if (n instanceof Long) {
            return new BigDecimal((long) n);
        }
        if (n instanceof Double) {
            return new BigDecimal((double) n);
        }
        if (n instanceof Float) {
            return new BigDecimal((float) n);
        }
        return null;
    }

}
