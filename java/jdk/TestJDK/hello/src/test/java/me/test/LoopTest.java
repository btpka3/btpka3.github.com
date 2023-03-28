package me.test;

import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;

public class LoopTest {
    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        long i = 0;
        for (; i < Integer.MAX_VALUE; i++) {

        }
        System.out.println(i);
        long end = System.currentTimeMillis();
        Period period = new Period((end - start) * ((long) (Math.pow(2, 33))));
        String time = ISOPeriodFormat.standard().print(period);

        // 大约 170 年
        System.out.println("从Long的最小值空循环到Long的最大值，大约需要的时间为：" + time);
    }

}
