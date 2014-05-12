
package me.test;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;

public class ListMail {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

        ldapTemplate.search(
                LdapQueryBuilder.query().where("objectClass").is("person"),

                new AttributesMapper<Void>() {

                    public Void mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute mailAttr = attrs.get("mail");
                        Attribute nameAttr = attrs.get("cn");
                        System.out.printf("%s - %s%n",
                                mailAttr == null ? "" : mailAttr.get(),
                                nameAttr == null ? "" : nameAttr.get()
                                );

                        return null;
                    }
                });

        System.out.println("===============");

    }

}
