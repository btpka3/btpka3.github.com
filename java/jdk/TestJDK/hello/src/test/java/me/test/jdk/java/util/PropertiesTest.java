package me.test.jdk.java.util;

import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) throws Exception {

        Properties props1 = new Properties();
        props1.load(PropertiesTest.class.getResourceAsStream("a.properties"));
        System.out.println(props1);

//        Properties props2 = new Properties(props1);
//        props2.load(PropertiesTest.class.getResourceAsStream("b.properties"));
//        System.out.println(props2);

        props1.load(PropertiesTest.class.getResourceAsStream("b.properties"));
        System.out.println(props1);
    }
}
