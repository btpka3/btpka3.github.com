package me.test.jdk.java.time;

import java.time.OffsetDateTime;

/**
 * @author 当千
 * @date 2019-04-24
 */
public class OffsetDateTimeTest {

    public static void main(String[] args) {

        test01();
    }

    static void test01() {

        System.out.println("============================= test01");
        OffsetDateTime.now().getOffset();

        System.out.println("zonedDateTime.toInstant().toEpochMilli()    : ");
    }
}
