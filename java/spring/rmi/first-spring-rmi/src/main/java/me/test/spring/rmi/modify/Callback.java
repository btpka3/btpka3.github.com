package me.test.spring.rmi.modify;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {

	public void run() throws RemoteException;

}
