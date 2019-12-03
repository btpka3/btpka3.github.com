package me.test.jdk.java.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 */
public class InstantTest {


    public static void main(String[] args) {

        test01();

    }

    static void test01() {

        System.out.println("============================= test01");


        Date nowDate = new Date();
        Instant nowInstant = Instant.now();
        Instant nowInstantDefault = Instant.now(Clock.systemDefaultZone());
        TimeZone defaultZone = TimeZone.getDefault();

        long currentTimeMillis = System.currentTimeMillis();
        int offset = defaultZone.getOffset(currentTimeMillis);
        long currentTimeMillisGmt0 = currentTimeMillis - offset;

        System.out.println("currentTimeMillis                           : " + currentTimeMillis);
        System.out.println("offset                                      : " + offset);
        System.out.println("currentTimeMillisGmt0                       : " + currentTimeMillisGmt0);

        System.out.println("defaultZone                                 : " + defaultZone);

        System.out.println("nowDate                                     : " + nowDate);
        System.out.println("nowDate.getTime()                           : " + nowDate.getTime());


        System.out.println("nowInstant                                  : " + nowInstant);
        System.out.println("nowInstant.toEpochMilli()                   : " + nowInstant.toEpochMilli());
        System.out.println("nowInstantDefault                           : " + nowInstantDefault);
        System.out.println("nowInstantDefault.toEpochMilli()            : " + nowInstantDefault.toEpochMilli());


        ZonedDateTime zonedDateTime = nowInstant.atZone(defaultZone.toZoneId());


        System.out.println("zonedDateTime.toInstant().toEpochMilli()    : " + zonedDateTime.toInstant().toEpochMilli());
    }


}
