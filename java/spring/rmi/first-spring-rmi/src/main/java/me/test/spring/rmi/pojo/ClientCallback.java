package me.test.spring.rmi.pojo;

import java.util.List;

public class ClientCallback implements Runnable {
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
