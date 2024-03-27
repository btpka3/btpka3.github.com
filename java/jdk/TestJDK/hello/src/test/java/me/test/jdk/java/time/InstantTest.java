package me.test.jdk.java.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author dangqian.zll
 * @date 2022/1/7
 */
public class InstantTest {

    @Test
    public void test01() {

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

    /**
     * 获取GMT-0
     */
    @Test
    public void gmt0() {
        OffsetDateTime nowOdt = OffsetDateTime.now();
        OffsetDateTime gmt0Odt = nowOdt.minusSeconds(nowOdt.getOffset().getTotalSeconds());
        Date date = Date.from(gmt0Odt.toInstant());
        System.out.println(date);
        System.out.println(date.getTime());

    }
}
