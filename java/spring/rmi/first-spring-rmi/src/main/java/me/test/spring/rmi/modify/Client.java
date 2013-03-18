package me.test.spring.rmi.modify;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/modify/client-context.xml");
		ctx.start();

		ServerInterface myInterface = (ServerInterface) ctx
				.getBean("serverInterface");

		// TEST 1.1 RMI server can NOT modify the data client sent.
		// TEST 1.2 RMI server can invoke the callback function client provided
		// and running on client side.
		System.out.println("============================ TEST 1 START");
		List<String> clientData = new ArrayList<String>();
		clientData.add("C1");
		clientData.add("C2");
		ClientCallback clientCallback = new ClientCallback();
		clientCallback.setData(clientData);
		System.out
				.println("CLIENT: the data send to server is : " + clientData);
		Callback c = (Callback) UnicastRemoteObject.exportObject(
				clientCallback, 0);
		System.out.println(c);
		myInterface.execute(clientData, c);
		System.out.println("============================ TEST 1 END");
		System.out.println();

		// TEST 2.1 Client can NOT modify the data from RMI server.
		// TEST 2.2 Client can invoke the remote function and running on server
		// side.
		System.out.println("============================ TEST 2 START");
		List<String> serverData = myInterface.getData();
		System.out.println("CLIENT: server data is : " + serverData);
		serverData.add("C9");
		System.out.println("CLIENT: server data has been changed to : "
				+ serverData);

		// Can be called 'server callback', or 'indirectly invoked remote
		// function'?
		System.out.println("CLIENT: invoke server callback function");
		Callback serverCallback = myInterface.getServerCallback();
		// FIXME : sometimes throws NoSuchObjectException, Please read README.txt#REFERENCE 2.
		serverCallback.run();
		System.out.println("============================ TEST 2 END");
	}
}
