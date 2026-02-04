package me.test.jdk.java.text;

import java.text.DecimalFormat;

public class DecimalFormat01 {

    public static void main(String[] args) {
        System.out.println(new DecimalFormat("#,####.##").format(1234567890.123456));
        System.out.println(new DecimalFormat("0000.00").format(123.1));
    }
}
