package com.github.btpka3.first.felix.my.module.a.impl;


import com.github.btpka3.first.felix.my.module.a.api.Aaa;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = Aaa.class,configurationPolicy= ConfigurationPolicy.REQUIRE)
//@Designate(ocd = MyServiceConfiguration.class)
@ServiceDescription("My simple service")
@ServiceRanking(1001)
@ServiceVendor("Feike")
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

//
//    @Activate
//	private MyServiceConfiguration config;
//
//	private boolean author;
//
//	@Reference
//	private SlingSettingsService settings;
//
//	@Activate
//	public void activate(MyServiceConfiguration config) {
//		author = settings.getRunModes().contains("author");
//	}

}
