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

    @Override
    public String sayHello(String name) {
        RpcContext.getContext().setAttachment("tag1", "Hangzhou");
        String result = "Hello " + name + ", from " + getHostName() + " @ " + System.currentTimeMillis();
        System.out.println("=========== result = :" + result);
        return result;
    }

    @SneakyThrows
    protected String getHostName() {
        return InetAddress.getLocalHost().getHostName();
    }
}
