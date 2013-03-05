package me.test.jaas.platform;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PolicySpi;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.PropertyPermission;
import java.util.StringTokenizer;

/**
 * 自定义的安全策略实现。可以参考并修改之后从数据库中加载权限。
 * @author zhangliangliang
 *
 */
public class SecurityPolicySpi extends PolicySpi {
	private List<ProtectionDomain> cache = null;

	public SecurityPolicySpi(Policy.Parameters params) throws IOException {
		cache = new ArrayList<ProtectionDomain>();

		CodeSource cs = null;
		PermissionCollection pc = null;
		ProtectionDomain pd = null;
		URL url = null;

		// 先初始化默认安全策略
		initDefault();

		// 在自定义安全测略：
		// ----平台代码拥有所有权限
		url = new File("./deploy/platform.jar").getCanonicalFile().toURI()
				.toURL();
		cs = new CodeSource(url, (Certificate[]) null);
		pc = new Permissions();
		pc.add(SecurityConstants.ALL_PERMISSION);
		pd = new ProtectionDomain(cs, pc);
		cache.add(pd);

		// ----TaskA 可以对系统属性"java.version"进行读写操作
		url = new File("./deploy/provider_a.jar").getCanonicalFile().toURI()
				.toURL();
		cs = new CodeSource(url, (Certificate[]) null);
		pc = new Permissions();
		pc.add(new PropertyPermission("java.version", "read,write"));
		pd = new ProtectionDomain(cs, pc);
		cache.add(pd);
		
		// ----TaskB 按照默认设定，对系统属性"java.version"仅能够进行读操作
	}

	/**
	 *	解析系统属性 "java.ext.dirs"，获取其中每个目录的绝对URL。
	 */
	public static String[] parseExtDirs() throws IOException {
		final String extDir = "java.ext.dirs";
		String s = System.getProperty(extDir);
		List<String> dirs = new ArrayList<String>();
		if (s != null) {
			StringTokenizer st = new StringTokenizer(s, File.pathSeparator);
			int count = st.countTokens();
			for (int i = 0; i < count; i++) {
				String nextToken = st.nextToken();
				if (nextToken == null || nextToken.matches("^\\s*$")) {
					continue;
				}
				File file = new File(nextToken);
				String dir = null;
				try {
					dir = file.getCanonicalFile().toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				dir += dir.endsWith("/") ? "*" : "/*";
				dirs.add(dir);
			}
		}
		return dirs.toArray(new String[0]);
	}

	/**
	 * 等同于 ${java.ext.dirs}/java.policy 中的设定。
	 * 
	 * @throws IOException
	 */
	public void initDefault() throws IOException {
		PermissionCollection pc = null;
		CodeSource cs = null;
		ProtectionDomain pd = null;

		// Standard extensions get all permissions by default
		String[] extDirs = parseExtDirs();
		for (String dir : extDirs) {
			cs = new CodeSource(new URL(dir), (Certificate[]) null);
			pc = new Permissions();
			pc.add(SecurityConstants.ALL_PERMISSION);
			pd = new ProtectionDomain(cs, pc);
			cache.add(pd);
		}

		// default permissions granted to all domains
		// 参考: http://javasourcecode.org/html/open-source/jdk/jdk-6u23/sun/security/provider/PolicyFile.java.html
		cs = new CodeSource(null, (Certificate[]) null);
		pc = new Permissions();
		pc.add(SecurityConstants.STOP_THREAD_PERMISSION); // NOTICE

		pc.add(SecurityConstants.LOCAL_LISTEN_PERMISSION);
		pc.add(new PropertyPermission("java.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vendor",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vendor.url",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.class.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("os.name",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("os.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("os.arch",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("file.separator",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("path.separator",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("line.separator",
				SecurityConstants.PROPERTY_READ_ACTION));

		pc.add(new PropertyPermission("java.specification.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.specification.vendor",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.specification.name",
				SecurityConstants.PROPERTY_READ_ACTION));

		pc.add(new PropertyPermission("java.vm.specification.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vm.specification.vendor",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vm.specification.name",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vm.version",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vm.vendor",
				SecurityConstants.PROPERTY_READ_ACTION));
		pc.add(new PropertyPermission("java.vm.name",
				SecurityConstants.PROPERTY_READ_ACTION));

		pd = new ProtectionDomain(cs, pc);
		cache.add(pd);
	}

	@Override
	protected boolean engineImplies(final ProtectionDomain domain,
			Permission permission) {
		PermissionCollection pc = engineGetPermissions(domain);
		return pc.implies(permission);
	}

	@Override
	protected PermissionCollection engineGetPermissions(CodeSource codesource) {
		Permissions permissions = new Permissions();
		for (int i = 0; i < cache.size(); i++) {
			ProtectionDomain pd = cache.get(i);
			CodeSource cs = pd.getCodeSource();
			if (cs != null && cs.implies(codesource)) {
				Enumeration<Permission> e = pd.getPermissions().elements();
				while (e.hasMoreElements()) {
					Permission p = e.nextElement();
					permissions.add(p);
				}
			}
		}

		if (permissions.elements().hasMoreElements()) {
			return permissions;
		}
		return super.engineGetPermissions(codesource);
	}

	@Override
	protected PermissionCollection engineGetPermissions(ProtectionDomain domain) {
		return engineGetPermissions(domain.getCodeSource());
	}

	@Override
	protected void engineRefresh() {
		// TODO 重新查询数据库，以获取最新的安全策略配置。
		super.engineRefresh();
	}

}
