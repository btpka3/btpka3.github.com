package com.github.btpka3.dubbo.demo.provider.service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.btpka3.dubbo.demo.api.DemoService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name + " @ " + new Date() + " : " + RpcContext.getContext().getLocalAddress();
    }
}