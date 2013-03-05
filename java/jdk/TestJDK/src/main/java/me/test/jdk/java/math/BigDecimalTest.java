package me.test.jdk.java.math;

import java.math.BigDecimal;

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

}
