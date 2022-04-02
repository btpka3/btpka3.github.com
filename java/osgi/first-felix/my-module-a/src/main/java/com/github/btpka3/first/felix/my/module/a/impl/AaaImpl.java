package com.github.btpka3.first.felix.my.module.a.impl;


import com.github.btpka3.first.felix.my.module.a.api.Aaa;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@Component(
//        service = Aaa.class,
//        configurationPolicy = ConfigurationPolicy.REQUIRE,
//        immediate = true
)
//@Designate(ocd = MyServiceConfiguration.class)
@ServiceDescription("My simple service")
@ServiceRanking(1001)
@ServiceVendor("btpka3")
public class AaaImpl implements Aaa {
    @Reference(
            service = LoggerFactory.class,
            policy = ReferencePolicy.STATIC,
            cardinality = ReferenceCardinality.MANDATORY
    )
    private volatile Logger logger;

    @Override
    public String hello(String name) {
        String msg = "AaaImpl hello " + name;
        System.out.println(msg + ",");
        logger.info(StringUtils.trimToEmpty(msg) + ".");
        return msg;
    }

    @Activate
    public void open() {
        System.out.println("AaaImpl activated,");
        logger.info("AaaImpl activated.");
    }


    @Deactivate
    public void close() {
        System.out.println("AaaImpl deactivated,");
        logger.info("AaaImpl deactivated.");
    }
}