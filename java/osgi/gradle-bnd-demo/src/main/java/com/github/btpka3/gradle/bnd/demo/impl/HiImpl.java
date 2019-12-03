package com.github.btpka3.gradle.bnd.demo.impl;


import com.github.btpka3.gradle.bnd.demo.api.Hi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HiImpl implements Hi {

    private static Log log = LogFactory.getLog(HiImpl.class);

    @Override
    public String hi(String name) {
        String msg = "hi, " + name + " ~";
        log.info(msg);
        return msg;
    }

    public void startUp() {
        System.out.println("aaa service startup");
    }

}
