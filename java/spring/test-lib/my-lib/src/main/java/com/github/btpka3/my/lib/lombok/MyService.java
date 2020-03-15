package com.github.btpka3.my.lib.lombok;

import lombok.SneakyThrows;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
public class MyService {

    @SneakyThrows
    public static void testException() {
        throw new Exception("aaa-" + System.currentTimeMillis());
    }
}
