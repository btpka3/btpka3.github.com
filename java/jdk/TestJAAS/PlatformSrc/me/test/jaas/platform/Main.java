/**
 *  JaaS可以做到通过启用安全管理器，对不信任的第三方代码进行权限约束。
 *  JDK中所有的权限可以使用JDK自带的policytool.exe进行查看、配置，并生成策略文件。
 *  但是如果需要作为一个平台、插件系统动态的追加删除各种第三方代码，静态的策略文件就有点不适合了。
 *  这里的代码就是尝试使用动态编程的方式来配置安全管理器和安全策略。
 *
 * 权限设计：
 *   平台代码拥有所有权限。
 *   TaskA 对系统属性"java.version"拥有读取和写入权限。
 *   TaskB 对系统属性"java.version"仅仅拥有读取权限。
 *
 * 测试用例：
 *   1. TaskB调用TaskA的非特权代码，则无法对系统属性"java.version"进行修改。
 *   2. TaskB调用TaskA的特权代码，则可以对系统属性"java.version"进行修改。
 *
 * 注意：
 *   ※ 系统属性"java.version"在启用安全管理器的情况下，默认是只拥有读取权限的。
 *   ※ JDK自带的安全策略PolicySpi的实现是位于 sun.security.provider.Sun中：
 *      sun.security.provider.Sun           -> java.security.Provider
 *   	sun.security.provider.PolicySpiFile -> java.security.PolicySpi
 *      sun.security.provider.PolicyFile    -> java.security.Policy
 *     相应的代码可以参考：
 *       http://javasourcecode.org/html/open-source/jdk/jdk-6u23/sun/security/provider/
 *   ※ 如果要作为一个平台，对所有第三方代码一个一视同仁的安全策略设定，且第三方代码
 *     都位于同一个目录下（或其子目录下），则使用JDK自带的静态安全配置就完全可以的：
 *       CodeBase要使用 "file:/dir/dir/*" 或 "file:/dir/dir/-"。
 *     该说明请参考java.security.CodeSource#implies()方法的Javadoc，或者其他参考的第一个文档。
 *   ※ 其他参考：
 *       http://docs.oracle.com/javase/6/docs/technotes/guides/security/jaas/tutorials/index.html
 *       http://docs.oracle.com/javase/1.5.0/docs/guide/security/PolicyFiles.html
 */
package me.test.jaas.platform;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;
import java.security.Policy;
import java.security.Provider;
import java.security.Security;
import java.util.PropertyPermission;
import java.util.logging.Logger;

public class Main {
	private static final String name = "PLATFORM  ";
	private static final String value = "PPPPPPPP";

	Logger logger = Logger.getLogger(Main.class.getName());

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		// 初始化
		initSecurityPolicy();

		// 确认
		for (Provider p : Security.getProviders()) {
			System.out.printf("%10s : %3s : %s\r\n", p.getName(), p
					.getVersion(), p.getClass());
		}

		// 启用安全策略
		SecurityManager sm = new SecurityManager();
		System.setSecurityManager(sm);

		// 载入所有的的Class（可以通过使用自定义的ClassLoader来）
		// 注意：使用同一个classLoader加载，否则TaskB找不到TaskA。
		URL url_a = new File("./deploy/provider_a.jar").getCanonicalFile()
				.toURI().toURL();
		URL url_b = new File("./deploy/provider_b.jar").getCanonicalFile()
				.toURI().toURL();
		ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { url_a,
				url_b });

		Class taskAClass = classLoader
				.loadClass("me.test.jaas.provider.a.TaskA");

		Class taskBClass = classLoader
				.loadClass("me.test.jaas.provider.b.TaskB");

		checkPermission(taskAClass, taskBClass);

		System.out.println();
		checkRun(taskAClass, taskBClass);

		System.out.println();
		checkInvoke(taskAClass, taskBClass);
	}

	/**
	 * 检查权限
	 */
	@SuppressWarnings("unchecked")
	public static void checkPermission(Class a, Class b) throws Exception {
		System.out.println("==========================检查权限");

		// Platform
		Main.check();

		// provider A
		Method m = a.getMethod("check", (Class[]) null);
		m.invoke(null, (Object[]) null);
		// provider B
		m = b.getMethod("check", (Class[]) null);
		m.invoke(null, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	public static void checkRun(Class a, Class b) throws Exception {
		System.out.println("==========================单独运行测试");
		Object obj = a.newInstance();
		Method m = a.getMethod("run", (Class[]) null);
		m.invoke(obj, (Object[]) null);

		obj = b.newInstance();
		m = b.getMethod("run", (Class[]) null);
		m.invoke(obj, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	public static void checkInvoke(Class a, Class b) throws Exception {
		System.out.println("==========================相互调用测试");
		Main main = new Main();
		main.run();

		Object obj = b.newInstance();
		Method m = b.getMethod("callAWithoutPrivileged", (Class[]) null);
		m.invoke(obj, (Object[]) null);

		m = b.getMethod("callAWithtPrivileged", (Class[]) null);
		m.invoke(obj, (Object[]) null);
	}

	public static void initSecurityPolicy() throws MalformedURLException,
			NoSuchAlgorithmException {

		// 注册一个自定义的Provider，但是仅仅提供 "Policy.JavaPolicy"
		@SuppressWarnings("serial")
		Provider provider = new Provider("btpka3", 1.0d,
				"initialization in progress") {
		};
		provider.put("Policy.JavaPolicy",
				"me.test.jaas.platform.SecurityPolicySpi");
		Security.insertProviderAt(provider, 1); // 注意：索引从1开始。

		// 使用我们定义的策略获取类，并将其作为系统的策略设定。
		Policy p = Policy.getInstance("JavaPolicy", null);
		Policy.setPolicy(p);

		// 如果需要在某个指定的时刻重新加载安全策略设定，则可以：
		// p.refresh();

		// 这个怎么用？
		// System.setProperty("policy.provider",
		// "me.test.jaas.platform.SecurityPolicy");
	}

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
		String version = System.getProperty("java.version");
		System.out.println(name + " : java.version = " + version);

		boolean success = false;
		try {
			System.setProperty("java.version", value);
			success = true;
		} catch (Exception e) {
		}
		String resultStr = success ? "SUCCESS" : "FAILED";
		System.out.println(name + " : setProperty(\"java.version\" = \""
				+ value + "\" = " + resultStr);
		version = System.getProperty("java.version");
		System.out.println(name + " : java.version = " + version);
	}
}
