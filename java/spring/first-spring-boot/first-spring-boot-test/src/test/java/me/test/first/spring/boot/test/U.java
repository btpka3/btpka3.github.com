package me.test.first.spring.boot.test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dangqian.zll
 * @date 2024/7/12
 */
public class U {

    public static String getIsoTimeStr() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
