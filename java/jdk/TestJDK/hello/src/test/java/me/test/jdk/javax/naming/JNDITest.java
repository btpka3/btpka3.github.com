package me.test.jdk.javax.naming;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 * @see org.springframework.jndi.JndiObjectFactoryBean
 * @see org.springframework.jndi.JndiTemplate
 */
public class JNDITest {

    /* Context.INITIAL_CONTEXT_FACTORY :

    com.sun.jndi.ldap.LdapCtxFactory                : LDAP（轻量级目录访问协议）,用于与 LDAP 服务器（如 OpenLDAP、Active Directory）交互：
    com.sun.jndi.rmi.registry.RegistryFactory       : RMI
    com.sun.jndi.fscontext.RefFSContextFactory      : 文件系统,将本地文件系统作为命名服务（用于测试或简单场景）：
    com.sun.jndi.cosnaming.CNCtxFactory             : CORBA（通用对象请求代理结构）
    com.ibm.websphere.naming.WsnNameServiceFactory  : WebSphere
    weblogic.jndi.WLInitialContextFactory           : WebLogic
    org.jboss.as.naming.InitialContextFactory       : JBoss/WildFly：
    org.apache.naming.java.javaURLContextFactory    : Tomcat（通过 JNDI 绑定、查找资源）：
    com.sun.jndi.url.http.HttpCtxFactory            : HTTP 命名服务（实验性）：

     */

    /**
     * 查询 DNS 服务
     */
    @Test
    @SneakyThrows
    public void testLookupDns01() {

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        // 使用 DNS 服务器地址
        env.put(Context.PROVIDER_URL, "dns://114.114.114.114");

        DirContext ctx = new InitialDirContext(env);
        Attributes res = ctx.getAttributes("example.com", new String[] {"A"});
        System.out.println(res);
    }

    /**
     * 查询 LDAP
     */
    @Test
    @SneakyThrows
    public void testLookupLdap01() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // LDAP 服务器地址
        env.put(Context.PROVIDER_URL, "ldap://localhost:8080");

        DirContext ctx = new InitialDirContext(env);
        DirContext lookCtx = (DirContext) ctx.lookup("cn=john,ou=employees,dc=example,dc=com");
        Attributes res = lookCtx.getAttributes("");
        System.out.println(res);
    }

    /**
     * 查询 LDAP
     */
    @Test
    @SneakyThrows
    public void testLookupRmi01() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
    }

    @Test
    @SneakyThrows
    public void testJndiObject01() {
        Name objectName = new CompositeName("java:comp/env");
        Enumeration<String> elements = objectName.getAll();
        while (elements.hasMoreElements()) {
            System.out.println(elements.nextElement());
        }
        Hashtable<String, String> env = new Hashtable<>();
        // env.put(Context.PROVIDER_URL, null);
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        Context ctx = new InitialContext(env);
        // 注册服务
        {
            MyService myService = new MyService();
            // 如果没指定 Context.INITIAL_CONTEXT_FACTORY , 会报错。
            // javax.naming.NoInitialContextException: Need to specify class name in environment or system property,
            //  or in an application resource file: java.naming.factory.initial
            ctx.bind("java:comp/env/MyService", myService);
        }
        // 调用服务
        {
            MyService myService = (MyService) ctx.lookup("java:comp/env/MyService");
            String result = myService.hi("zhang3");
            Assertions.assertEquals("hi zhang3", result);
        }
    }

    public class MyService {
        public String hi(String name) {
            return "hi " + name;
        }
    }
}
