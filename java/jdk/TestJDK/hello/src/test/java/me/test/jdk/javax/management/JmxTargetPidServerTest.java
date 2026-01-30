package me.test.jdk.javax.management;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/5
 */
@Slf4j
public class JmxTargetPidServerTest {
    long jmxPort = 9010;

    @SneakyThrows
    @Test
    public void startDemoTargetJvm() {
        /*
        java -Dcom.sun.management.jmxremote \
         -Dcom.sun.management.jmxremote.port=9010 \
         -Dcom.sun.management.jmxremote.authenticate=true \
         -Dcom.sun.management.jmxremote.ssl=true \
         -Dcom.sun.management.jmxremote.password.file=/opt/jmx/jmxremote.password \
         -Dcom.sun.management.jmxremote.access.file=/opt/jmx/jmxremote.access \
         -Djava.rmi.server.hostname=192.168.1.100 \
         -jar your-app.jar
         */

        assertTrue(System.getProperties().containsKey("com.sun.management.jmxremote"));
        assertEquals("9010", System.getProperty("com.sun.management.jmxremote.port"));
        assertEquals("false", System.getProperty("com.sun.management.jmxremote.authenticate"));
        assertEquals("false", System.getProperty("com.sun.management.jmxremote.ssl"));

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        log.info("======= pid={}, please test jmx with this.", runtimeMXBean.getPid());
        Thread.sleep(60 * 60 * 1000L);
    }


    @Test
    public void getJvmProps01() {

        long pid = 41952;
        log.info("============ target pid={}", pid);
        {
            Properties props = getJvmProps(pid);
            log.info("============ props:");
            props.forEach((k, v) -> {
                log.info("    {}={}", k, v);
            });
        }
        {
            List<String> props = getJvmArguments(pid);
            log.info("============ arguments:");
            props.forEach((v) -> {
                log.info("    {}", v);
            });
        }
    }

    @SneakyThrows
    public Properties getJvmProps(long pid) {
        Properties props = new Properties();
        String jmxUrl = String.format("service:jmx:rmi:///jndi/rmi://:" + jmxPort + "/jmxrmi");
        JMXServiceURL url = new JMXServiceURL(jmxUrl);
        try (JMXConnector connector = JMXConnectorFactory.connect(url)) {
            if (connector != null) {
                MBeanServerConnection connection = connector.getMBeanServerConnection();
                RuntimeMXBean runtimeBean = ManagementFactory.newPlatformMXBeanProxy(
                        connection,
                        ManagementFactory.RUNTIME_MXBEAN_NAME,
                        RuntimeMXBean.class
                );
                props.putAll(runtimeBean.getSystemProperties());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get JVM properties for pid: " + pid, e);
        }
        return props;
    }

    @SneakyThrows
    public List<String> getJvmArguments(long pid) {
        String jmxUrl = String.format("service:jmx:rmi:///jndi/rmi://:" + jmxPort + "/jmxrmi");
        JMXServiceURL url = new JMXServiceURL(jmxUrl);
        try (JMXConnector connector = JMXConnectorFactory.connect(url)) {
            if (connector != null) {
                MBeanServerConnection connection = connector.getMBeanServerConnection();
                RuntimeMXBean runtimeBean = ManagementFactory.newPlatformMXBeanProxy(
                        connection,
                        ManagementFactory.RUNTIME_MXBEAN_NAME,
                        RuntimeMXBean.class
                );
                return runtimeBean.getInputArguments();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get JVM options for pid: " + pid, e);
        }
        return Collections.emptyList();
    }

}
