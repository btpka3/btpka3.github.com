package me.test.jdk.java.rmi;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 */
public class RmiTest {

    @Test
    @SneakyThrows
    public void statServer() {
        MyRmiService service = new MyRmiServiceImpl();
        MyRmiService skeleton = (MyRmiService) UnicastRemoteObject.exportObject(service, 0);

        Registry registry = LocateRegistry.createRegistry(8888);
        registry.bind("MyRmiService", skeleton);
        System.out.println("server start success");
        Thread.sleep(60 * 60 * 1000);
    }

    @Test
    @SneakyThrows
    public void testClient() {
        MyRmiService service = (MyRmiService) Naming.lookup("rmi://localhost:8888/MyRmiService");
        String result = service.sayHello("zhang3");
        Assertions.assertEquals("hi zhang3", result);
    }


}
