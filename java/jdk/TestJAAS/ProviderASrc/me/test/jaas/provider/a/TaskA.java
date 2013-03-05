package me.test.jaas.provider.a;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.PropertyPermission;

/**
 * 该代码对系统属性"java.version"既有读取权限，也有写入权限。
 */
public class TaskA {
	private static final String name = "PROVIDER_A";
	private static final String value = "AAAAAAAA";

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
	 * 尝试对系统属性"java.version"先后进行读-写-读的操作。（非特权模式）
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
	 * 尝试对系统属性"java.version"先后进行读-写-读的操作。（特权模式）
	 */
	@SuppressWarnings("unchecked")
	public void runInPrivileged() {
		System.out.println(name + " : ------------------------");

		// 读取
		String version = System.getProperty("java.version");
		System.out.println(name + " : java.version = " + version);

		// 写入
		boolean success = false;
		try {
			AccessController.doPrivileged(new PrivilegedAction() {
				public Object run() {
					System.setProperty("java.version", value);
					return null;
				}
			});
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

}
