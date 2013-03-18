package me.test.spring.rmi.modify;

import java.util.List;

public interface ServerInterface {
	void execute(List<String> data, Callback callback);

	List<String> getData();

	Callback getServerCallback();
}
