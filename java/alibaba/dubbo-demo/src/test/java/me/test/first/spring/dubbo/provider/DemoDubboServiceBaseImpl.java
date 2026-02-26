package me.test.first.spring.dubbo.provider;

import lombok.SneakyThrows;
import me.test.first.spring.dubbo.DemoDubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.net.InetAddress;

/**
 * @author dangqian.zll
 * @date 2023/5/12
 */
public class DemoDubboServiceBaseImpl implements DemoDubboService {

    String hostName = getHostName();

    @Override
    public String sayHello(String name) {
        long startTime = System.currentTimeMillis();
        RpcContext.getContext().setAttachment("tag1", "Hangzhou");
        String result = "Hello " + name + ", from " + hostName + " @ " + System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        System.out.println("=========== result = :" + result + ", cost=" + (endTime - startTime) + "ms");
        return result;
    }

    @SneakyThrows
    protected String getHostName() {
        long startTime = System.currentTimeMillis();
        String name = InetAddress.getLocalHost().getHostName();
        long endTime = System.currentTimeMillis();
        System.out.println("=========== getHostName = :" + name + ", cost=" + (endTime - startTime) + "ms");
        System.out.println("=========== System.getProperty(\"hostname\") = :" + System.getProperty("hostname"));
        return name;
    }
}
