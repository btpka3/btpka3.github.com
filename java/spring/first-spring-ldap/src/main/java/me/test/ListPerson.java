
package me.test;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;

public class ListPerson {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");
        list(ldapTemplate);

    }

    public static void list(LdapTemplate ldapTemplate) {

        ldapTemplate.search(
                LdapQueryBuilder.query().where("objectCategory").is("CN=Person,CN=Schema,CN=Configuration,DC=tcgroup,DC=local"),

                new AttributesMapper<String>() {

                    public String mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute dn = attrs.get("mail");
                        if (dn != null) {
                            System.out.printf("%s %n", dn.get());
                        } else {
                            System.out.println("?????????????????????????????????");
                        }
                        return null;
                    }
                });

        System.out.println("===============");
    }

}
