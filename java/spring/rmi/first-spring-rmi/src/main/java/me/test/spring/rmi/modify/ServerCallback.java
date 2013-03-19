package me.test.spring.rmi.modify;

import java.util.List;

public class ServerCallback implements Callback {
	private List<String> data;

	public void run() {
		// This should be printed in the SERVER console.
		System.out.println("SERVER: call back : the client data is : " + data);
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
