package com.alibaba.code.u158230.mtee.scan.clazz.biz.service;

import lombok.SneakyThrows;

/**
 * @author dangqian.zll
 * @date 2024/10/29
 */
public class HelloDemoTest {
    @SneakyThrows
    public static void main(String[] args) {
        try {
            HelloDemo helloDemo = new HelloDemo();
            System.out.println("=====HI:" + helloDemo.hello("world"));
        } catch (Throwable e) {
            System.out.println("=====ERR:" + e.getMessage());
            e.printStackTrace();
        }

        Thread.sleep(60 * 1000 * 10);
    }
}
