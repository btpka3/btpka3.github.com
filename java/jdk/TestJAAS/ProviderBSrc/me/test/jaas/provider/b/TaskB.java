package me.test.jaas.provider.b;

import java.util.PropertyPermission;

import me.test.jaas.provider.a.TaskA;

/**
 * 该代码对系统属性"java.version"仅有读取权限。
 * 但是如果TaskA提供相应的特权接口，TaskB可以通过该接口按照TaskA的规则行使相应的特权。
 */
public class TaskB {
	private static final String name = "PROVIDER_B";
	private static final String value = "BBBBBBBB";

	/**
	 * 检查对系统属性"java.version"是否有读写权限。
	 */
	public static void check() {
		boolean readOk = false;
		boolean writeOk = false;

		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			readOk = true;
			writeOk = true;
		} else {
			try {
				sm.checkPermission(new PropertyPermission("java.version",
						"read"));
				readOk = true;
			} catch (Exception e) {
			}
			try {
				sm.checkPermission(new PropertyPermission("java.version",
						"write"));
				writeOk = true;
			} catch (Exception e) {
			}
		}
		String resultStr = "[";
		resultStr += readOk ? "R" : "";
		resultStr += writeOk ? "W" : "";
		resultStr += "]";

		System.out.println(name + " : property[\"java.version\"] = "
				+ resultStr);
	}

	/**
	 * 尝试对系统属性"java.version"先后进行读-写-读的操作。
	 */
	public void run() {
		System.out.println(name + " : ------------------------");
		// 读取
		String version = System.getProperty("java.version");
		System.out.println(name + " : java.version = " + version);

		// 写入
		boolean success = false;
		try {
			System.setProperty("java.version", value);
			success = true;
		} catch (Exception e) {

		}
		String resultStr = success ? "SUCCESS" : "FAILED";
		System.out.println(name + " : setProperty(\"java.version\", \"" + value
				+ "\") = " + resultStr);

		// 读取
		version = System.getProperty("java.version");
		System.out.println(name + " : java.version = " + version);
	}

	/**
	 * 调用A的非特权模式代码。
	 */
	public void callAWithoutPrivileged() {
		System.out.println(name + " : /*************调用A（A不使用特权模式）");
		TaskA taskA = new TaskA();
		taskA.run();
		System.out.println(name + " :  ****************************/");
	}

	/**
	 * 调用A的特权模式代码。
	 */
	@SuppressWarnings("unchecked")
	public void callAWithtPrivileged() {
		System.out.println(name + " : /*************调用A（A使用特权模式）");
		final TaskA taskA = new TaskA();
		taskA.runInPrivileged();
		System.out.println(name + " :  ****************************/");
	}
}
