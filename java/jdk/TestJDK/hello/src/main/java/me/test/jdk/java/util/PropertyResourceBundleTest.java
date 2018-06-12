package me.test.jdk.java.util;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @see java.util.PropertyResourceBundle
 */
public class PropertyResourceBundleTest {

    public static void main(String[] args) throws Exception {

        //ResourceBundle rb = ResourceBundle.getBundle("me/test/jdk/java/util/messages");
        //ResourceBundle rb = ResourceBundle.getBundle("me.test.jdk.java.util.messages");
        ResourceBundle rb = new PropertyResourceBundle(new InputStreamReader(PropertyResourceBundleTest.class.getResourceAsStream("/me/test/jdk/java/util/messages.properties"), "UTf-8"));

        System.out.println(rb.keySet());
        System.out.println("M01=" + rb.getString("M01"));
        System.out.println("M01=" + rb.getString("M02"));
        System.out.println("M01=" + rb.getString("M03"));
        System.out.println("M04=" + rb.getString("M04"));
        System.out.println("================");

        System.out.println("FMT:M01=" + MessageFormat.format(rb.getString("M01"), "aa"));
        System.out.println("FMT:M02=" + MessageFormat.format(rb.getString("M02"), "aa"));
        System.out.println("FMT:M03=" + MessageFormat.format(rb.getString("M03"), "aa"));
        System.out.println("FMT:M04=" + MessageFormat.format(rb.getString("M04"), "aa"));
    }
}
