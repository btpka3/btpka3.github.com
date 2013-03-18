package me.test.spring.rmi.modify;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerInterfaceImpl implements ServerInterface {
	private List<String> serverData = null;

	private Callback serverCallback = null;

	public ServerInterfaceImpl() {
		serverData = new ArrayList<String>();
		serverData.add("S1");
		serverData.add("S2");
		ServerCallback callback = new ServerCallback();
		callback.setData(serverData);
		try {
			serverCallback = (Callback) UnicastRemoteObject.exportObject(
					callback, 0);
			System.out.println(serverCallback);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: to add security manager to avoid 3rd party risk code.
	public void execute(List<String> clientData, Callback callback) {
		System.out.println("SERVER: ====================START execute().");
		System.out.println("SERVER: client data received is : " + clientData);
		clientData.add("S9");
		System.out.println("SERVER: client data has been changed to : "
				+ clientData);
		System.out.println("SERVER: call client callback.");
		try {
			callback.run();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		System.out.println("SERVER: ====================END execute().");
		System.out.println();
	}

	public List<String> getData() {
		return serverData;
	}

	public Callback getServerCallback() {
		System.out.println("~~~~~~~~~");
		return serverCallback;
	}
}
