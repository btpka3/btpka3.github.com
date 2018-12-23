package com.github.btpka3.demo.lib;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    /**
     * 配置 JVM 参数 "-Dmy.prop=file:/data0/work/git-repo/github/btpka3/btpka3.github.com/java/maven/demo-maven-app/demo-lib/src/test/resources/file-sys-conf.properties"
     * 时，将使用 file-sys-conf.properties 中的内容
     * 否则，将使用 conf.properties 中的内容
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("app.xml");

        System.out.println(appCtx.getBean("map"));

    }
}
