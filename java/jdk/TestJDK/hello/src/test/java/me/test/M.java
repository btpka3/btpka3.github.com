package me.test;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zll on 02/09/2017.
 */
public class M {

    public static void main(String[]args) throws UnsupportedEncodingException {

        System.out.println("xx"+new String (new byte[]{0x03,0x01},"GBK")+"~~");
    }

}
