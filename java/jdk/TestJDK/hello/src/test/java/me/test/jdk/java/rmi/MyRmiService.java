package me.test.jdk.java.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 */
public interface MyRmiService extends Remote {

    /**
     * RMI 服务的方法需要声明抛出 RemoteException
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    String sayHello(String name) throws RemoteException;
}
