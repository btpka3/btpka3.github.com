package me.test.jdk.java.text;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class MessageFormat01 {

    public static void main(String[] args) {

        int planet = 7;
        String event = "a disturbance in the Force";

        String result = MessageFormat.format(
                "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                planet, new Date(), event);

        System.out.println(result);

        System.out.println(MessageFormat.format(
                "percent =  {0,number,percent}",
                1.23456));

        // 参考  java.text.DecimalFormat， 整数部分按照4个数字一组
        System.out.println(MessageFormat.format(
                "AAA : {0,number,#,####.##}",
                1234567890.123456));

        // 参考  java.text.DecimalFormat， 整数部分按照3个数字一组
        System.out.println(MessageFormat.format(
                "AAB : {0,number,#,###.##}",
                1234567890.1));

        // 参考  java.text.DecimalFormat， 整数部分按照3个数字一组
        System.out.println(MessageFormat.format(
                "AAC : {0,number,0000.00}",
                123.1));

        // 参考 SimpleDateFormat
        System.out.println(MessageFormat.format(
                "DDA : {0,date,yyyy-MM-dd HH:mm:ss.SS}",
                new Date()));

        // 不允许出现 "{}"
        System.out.println(MessageFormat.format(
                "DDA : {0},{1},{2}",
                "aaa",
                "bbb",
                "ccc"
        ));


    }
}
