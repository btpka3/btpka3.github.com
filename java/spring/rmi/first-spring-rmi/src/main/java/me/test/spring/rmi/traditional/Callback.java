package me.test.spring.rmi.traditional;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {

	public void run() throws RemoteException;

}
