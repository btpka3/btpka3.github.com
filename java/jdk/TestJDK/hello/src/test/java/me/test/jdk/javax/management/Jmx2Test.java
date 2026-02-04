package me.test.jdk.javax.management;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/30
 */
public class Jmx2Test {

    public void dumpHeap() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        // 2. 获取 HotSpotDiagnosticMXBean
        ObjectName name = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
        HotSpotDiagnosticMXBean mxBean =
                MBeanServerInvocationHandler.newProxyInstance(mbs, name, HotSpotDiagnosticMXBean.class, true);

        // 3. 执行命令（例如生成堆转储）
        mxBean.dumpHeap("heapdump.hprof", true); // 生成堆转储文件
    }
}
