package me.test.first.jmx;


import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class MyClient {
    public static void main(String[] args) throws Exception,
            NullPointerException {
        JMXServiceURL url = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        ObjectName objName = new ObjectName("MyServer:type=Hello");
        MyServerMBean s = JMX.newMBeanProxy(mbsc, objName, MyServerMBean.class, true);
        s.start();
        System.out.println("Client: start");
        Thread.sleep(5000);
        s.stop();
        System.out.println("Client: stop");
    }
}