
package me.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapAuth {

    public static void main(String[] args) {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldaps://10.1.10.2:636/DC=TCGROUP,DC=LOCAL");

        // Authenticate as S. User and password "mysecret"
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put("java.naming.ldap.factory.socket", "me.test.MySSLSocketFactory");
        env.put("java.naming.security.protocol", "ssl");

        env.put(Context.SECURITY_PRINCIPAL, "zhangliangliang@eetop.com");
        env.put(Context.SECURITY_CREDENTIALS, "{Mp3mp4}");

        try {
            DirContext ctx = new InitialDirContext(env);
            System.out.println("user and password matched");
        } catch (NamingException e) {
            System.err.println("user and password NOT match");
            e.printStackTrace();
        }

    }

}
