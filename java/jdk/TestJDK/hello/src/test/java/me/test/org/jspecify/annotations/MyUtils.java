package me.test.org.jspecify.annotations;

import org.jspecify.annotations.NullMarked;

import java.util.Properties;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/26
 */
@NullMarked
public class MyUtils {

    public Properties getProp() {
        return new Properties();
    }

    public boolean test() {
        Properties props = getProp();
        String value = props.getProperty("a");
        if (value == null) {
            return false;
        }
        return value.startsWith("xxx");
    }

}
