package me.test.jdk.java.security;

import java.security.Provider;

/**
 *
 *
 *
 * @author dangqian.zll
 * @date 2025/9/23
 *
 *
 */
public class MyProvider extends Provider {
    public static final String NAME = "btpka3";

    public MyProvider() {
        super(NAME, "1.0", "demo provider");
    }
}
