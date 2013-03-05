package me.test.first.jmx;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.Date;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

// http://docs.oracle.com/javase/1.5.0/docs/guide/management/overview.html
// http://docs.oracle.com/javase/tutorial/jmx/mbeans/mxbeans.html
// http://docs.oracle.com/javase/tutorial/jmx/index.html
// http://baike.soso.com/v7649834.htm
// http://weblogs.java.net/blog/emcmanus/archive/2006/11/a_real_example.html
// Standard MBeans, DynamicMBean Open MBeans, Model MBeans, MXBeans
/**
 * JMX约定： 1. 接口名一定时 XxxMBean 2. 实现类的名称一定是 Xxx
 */
public class MyServer implements MyServerMBean {
    public static void main(String[] args) throws Exception {

        // start RMI server
        // or CMD/> rmiregistry 9999
        LocateRegistry.createRegistry(9999);

        // setup MBeanServer
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        MyServer s = new MyServer();
        ObjectName objName = new ObjectName("MyServer:type=Hello");
        mbs.registerMBean(s, objName);

        // start JMXConnectorServer
        JMXServiceURL url = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnectorServer cs = JMXConnectorServerFactory
                .newJMXConnectorServer(url, null, mbs);
        cs.start();

        System.out.println("MBeanServer start");

    }

    private Thread t = null;

    public void start() {
        stop();
        t = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    System.out.println("Hello~ " + new Date());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Stopping...");
                        break;
                    }
                }
            }

        });
        t.start();

    }

    public void stop() {
        if (t != null && t.isAlive()) {
            t.interrupt();
        }
    }
}
