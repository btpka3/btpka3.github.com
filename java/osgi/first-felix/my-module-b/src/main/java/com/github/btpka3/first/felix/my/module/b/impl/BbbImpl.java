package com.github.btpka3.first.felix.my.module.b.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

public class BbbImpl {

    @Reference(service = LoggerFactory.class)
    private Logger logger;

    @Activate
    public void open() {
        System.out.println("BbbImpl activated,");
        logger.info("BbbImpl activated.");
    }

    @Deactivate
    public void close() {
        System.out.println("BbbImpl deactivated,");
        logger.info("BbbImpl deactivated.");
    }

}
