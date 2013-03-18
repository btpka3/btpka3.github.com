package me.test.spring.rmi.pojo;

import java.util.List;

public interface ServerInterface {
	void execute(List<String> data, Runnable callback);

	List<String> getData();

	Runnable getServerCallback();
}
