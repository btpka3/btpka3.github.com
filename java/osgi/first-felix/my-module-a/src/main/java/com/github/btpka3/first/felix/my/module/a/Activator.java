package com.github.btpka3.first.felix.my.module.a;


import org.osgi.framework.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LoggerFactory;


@Component
public class Activator implements BundleActivator, ServiceListener {
    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     *
     * @param context the framework context for the bundle.
     **/
    @Override
    public void start(BundleContext context) {
        System.out.println("Starting to listen for service events.");
        context.addServiceListener(this);

        try {
            ServiceReference<LoggerFactory> ref = context.getServiceReference(LoggerFactory.class);
            if (ref != null) {
                LoggerFactory loggerFactory = context.getService(ref);
                loggerFactory.getLogger(Activator.class).warn("MyActivator, wa wa wa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     *
     * @param context the framework context for the bundle.
     **/
    @Override
    public void stop(BundleContext context) {
        context.removeServiceListener(this);
        System.out.println("Stopped listening for service events.");

        // Note: It is not required that we remove the listener here,
        // since the framework will do it automatically anyway.
    }

    /**
     * Implements ServiceListener.serviceChanged().
     * Prints the details of any service event from the framework.
     *
     * @param event the fired service event.
     **/
    @Override
    public void serviceChanged(ServiceEvent event) {
        String[] objectClass = (String[])
                event.getServiceReference().getProperty("objectClass");

        if (event.getType() == ServiceEvent.REGISTERED) {
            System.out.println(
                    "Ex1: Service of type " + objectClass[0] + " registered.");
        } else if (event.getType() == ServiceEvent.UNREGISTERING) {
            System.out.println(
                    "Ex1: Service of type " + objectClass[0] + " unregistered.");
        } else if (event.getType() == ServiceEvent.MODIFIED) {
            System.out.println(
                    "Ex1: Service of type " + objectClass[0] + " modified.");
        }
    }
}