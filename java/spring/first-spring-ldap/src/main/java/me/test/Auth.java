
package me.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.LdapTemplate;

public class Auth {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");
        boolean result = ldapTemplate.authenticate("", "mail=zhangliangliang@eetop.com", "{Mp3mp4}");
        System.out.println("================" + result);
    }

}
