
package me.test;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;

public class ListDn {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

        ldapTemplate.search(
                LdapQueryBuilder.query().filter("objectClass=*"),

                new AttributesMapper<String>() {

                    public String mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute dn = attrs.get("distinguishedName");
                        if (dn != null) {
                            System.out.printf("%s %n", dn.get());
                        }
                        return null;
                    }
                });

        System.out.println("===============");

    }

}
