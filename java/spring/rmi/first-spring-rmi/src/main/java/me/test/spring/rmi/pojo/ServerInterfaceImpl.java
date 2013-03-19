package me.test.spring.rmi.pojo;

import java.util.ArrayList;
import java.util.List;

public class ServerInterfaceImpl implements ServerInterface {
	private List<String> serverData = null;

	private Runnable serverCallback = null;

	public ServerInterfaceImpl() {
		serverData = new ArrayList<String>();
		serverData.add("S1");
		serverData.add("S2");
		ServerCallback callback = new ServerCallback();
		callback.setData(serverData);
		// serverCallback = callback;
		serverCallback = RMIUtil.registerAndExport(callback, Runnable.class);
		System.gc(); // Try GC, then start client after a few seconds.
	}

	public void execute(List<String> clientData, Runnable callback) {
		System.out.println("SERVER: ====================START execute().");
		System.out.println("SERVER: client data received is : " + clientData);
		clientData.add("S9");
		System.out.println("SERVER: client data has been changed to : "
				+ clientData);
		System.out.println("SERVER: call client callback.");
		callback.run();
		System.out.println("SERVER: ====================END execute().");
		System.out.println();
	}

	public List<String> getData() {
		return serverData;
	}

	public Runnable getServerCallback() {
		System.out.println("~~~~~~~~~");
		return serverCallback;
	}
}
