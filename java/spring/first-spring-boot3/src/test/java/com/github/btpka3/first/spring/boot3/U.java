package com.github.btpka3.first.spring.boot3;

import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dangqian.zll
 * @date 2024/7/12
 */
public class U {

    public static String now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static void print(String step, Object data) {
        System.out.printf(
                "[%s][%4d - %30s] - %10s : %s%n",
                now(), Thread.currentThread().getId(), Thread.currentThread().getName(), step, data);
    }

    public static void print(String step, Object data, Throwable e) {
        print(step, data);
        if (e != null) {
            e.printStackTrace();
        }
    }

    public static String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        int len = password.length();
        int prefixLen = len > 18 ? 6 : (len > 6 ? 3 : (len > 2 ? 1 : 0));

        if (prefixLen == 0) {
            return StringUtils.repeat("*", len);
        }
        return password.substring(0, prefixLen)
                + StringUtils.repeat("*", len - prefixLen * 2)
                + password.substring(len - prefixLen);
    }
}
