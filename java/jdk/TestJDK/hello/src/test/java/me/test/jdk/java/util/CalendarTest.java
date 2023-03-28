package me.test.jdk.java.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 当千
 * @date 2019-04-24
 */
public class CalendarTest {

    public static void main(String[] args) {
        test01();
    }


    public static void test01() {

        System.out.println("============================= test01");

        Calendar c = Calendar.getInstance();

        Date date1 = c.getTime();
        System.out.println("date1           : " + date1);
        System.out.println("date1.getTime() : " + date1.getTime());

        c.add(Calendar.MILLISECOND, -c.getTimeZone().getOffset(c.getTimeInMillis()));
        Date date2 = c.getTime();
        System.out.println("date2           : " + date2);
        System.out.println("date2.getTime() : " + date2.getTime());

    }
}
