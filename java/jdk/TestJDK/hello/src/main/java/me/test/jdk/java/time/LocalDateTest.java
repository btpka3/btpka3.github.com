package me.test.jdk.java.time;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author 当千
 * @date 2019-01-03
 */
public class LocalDateTest {

    public static void main(String[] args) {

        LocalDate ld0 = LocalDate.of(2018, 12, 12);

        // date -> locateDate
        LocalDate ld1 = new Date()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        System.out.println(ld0);
        System.out.println(ld1);
        System.out.println(DAYS.between(ld0, ld1));
    }
}
