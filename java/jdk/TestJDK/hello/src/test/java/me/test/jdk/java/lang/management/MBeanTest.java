package me.test.jdk.java.lang.management;

import org.junit.jupiter.api.Test;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/8/15
 */
public class MBeanTest {

    static final String OBJECT_NAME = "com.example:type=Hello";


    @Test
    public void test01() throws Exception {
        register();
        invoke();
    }

    protected void register() throws Exception {
        /*
-Dcom.sun.management.jmxremote=true
-Dcom.sun.management.jmxremote.port=1234
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
        */
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName(OBJECT_NAME);
        HelloMXBean mbean = new HellMXBeanImpl();
        mbs.registerMBean(mbean, name);
    }

    protected void invoke() throws Exception {
        String hostname = "localhost";
        int port = 1234;
        Map<String, Object> env = new HashMap<String, Object>();

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + hostname + ":" + port + "/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
        MBeanServerConnection conn = jmxc.getMBeanServerConnection();
        ObjectName name = new ObjectName(OBJECT_NAME);
        // FIXME getAttribute 报错: "javax.management.AttributeNotFoundException: No such attribute: age"
//         System.out.println("age=" + conn.getAttribute(name, "age"));
        int x = 1;
        int y = 2;
        int result = (int) conn.invoke(name, "add", new Object[]{x, y}, new String[]{"int", "int"});
        System.out.println("add : x=" + x + ", y=" + y + ", result=" + result);
    }


    public static interface HelloMXBean {
        int add(int x, int y);

        int getAge();

        void setAge(int age);
    }

    public static class HellMXBeanImpl implements HelloMXBean {

        public int age = 4;

        @Override
        public int add(int x, int y) {
            return x + y;
        }

        @Override
        public int getAge() {
            return this.age;
        }

        @Override
        public void setAge(int age) {
            this.age = age;
        }
    }
}
