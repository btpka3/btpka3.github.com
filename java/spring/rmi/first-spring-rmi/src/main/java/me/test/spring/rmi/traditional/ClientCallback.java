package me.test.spring.rmi.traditional;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientCallback extends UnicastRemoteObject implements Callback {
	protected ClientCallback() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	private List<String> data;

	public void run() {
		// This should be printed in the CLIENT console.
		System.out.println("CLIENT: call back : the client data is : " + data);
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
