package me.test.jdk.java.util;

import java.io.IOException;
import java.util.Date;

/**
 */
public class FormatterTest1 {

    public static void main(String[] args) throws IOException {

        System.out.println("------------------------ String");
        System.out.println(" 0 : |" + String.format("%3s", "aa") + "|");
        System.out.println(" 0 : |" + String.format("%-3s", "aa") + "|");
        System.out.println(" 0 : |" + String.format("%3s", "aaaaa") + "|");

        System.out.println("------------------------ int/long");
        System.out.println(" 1 : |" + String.format("%3d", 1) + "|");
        System.out.println(" 2 : |" + String.format("%03d", 1) + "|");
        System.out.println(" 3 : |" + String.format("%-3d", 1) + "|");

        System.out.println("------------------------ float/double");
        System.out.println(" 4 : |" + String.format("%3.0f", 1.1) + "|");
        // 总共9个长度
        System.out.println(" 4 : |" + String.format("%9.2f", 1.345) + "|");
        // 总共前导0
        System.out.println(" 4 : |" + String.format("%09.2f", 1.345) + "|");
        // 左对齐
        System.out.println(" 4 : |" + String.format("%-9.2f", -1.345) + "|");
        // 千分位分组
        System.out.println(" 4 : |" + String.format("%-,20.2f", -123456789.345) + "|");

        System.out.println("------------------------ date");
        Date now = new Date();
        System.out.println(" d : |" + String.format("%1$tY-%1$tm-%1$td %1$TH:%1$TM:%1$TS.%1$TL %1$tz", now) + "|");


    }


}
