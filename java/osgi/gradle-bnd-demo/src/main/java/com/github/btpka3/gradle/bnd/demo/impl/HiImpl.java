package com.github.btpka3.first.felix.my.module.a.impl;


import com.github.btpka3.first.felix.my.module.a.api.Aaa;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AaaImpl implements Aaa {

    private static Log log = LogFactory.getLog(AaaImpl.class);

    @Override
    public String hello(String name) {
        String msg = "hello " + name;
        log.info(msg);
        return msg;
    }

    public void startUp(){
        System.out.println("aaa service startup");
    }

}
