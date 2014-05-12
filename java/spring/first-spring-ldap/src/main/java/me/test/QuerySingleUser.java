
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

public class QuerySingleUser {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

        ldapTemplate.search(
                LdapQueryBuilder.query().where("mail").is("zhangliangliang@eetop.com"),

                new AttributesMapper<Void>() {

                    public Void mapFromAttributes(Attributes attrs) throws NamingException {

                        NamingEnumeration<String> attrIdEnum = attrs.getIDs();
                        while (attrIdEnum.hasMoreElements()) {
                            String attrId = attrIdEnum.next();
                            Attribute attr = attrs.get(attrId);

                            System.out.println(attrId + ":");

                            // 以单个值的方式获取
                            // System.out.println("           //" + attr.get());

                            // 以多个值的方式获取
                            NamingEnumeration<?> nameEnum = attr.getAll();
                            while (nameEnum.hasMore()) {
                                Object valueObj = nameEnum.next();
                                System.out.printf("        %s%n",  valueObj);
                            }
                        }
                        return null;
                    }
                });

    }

}
/* ================================================ output:

whenCreated:
        20130218050850.0Z
objectCategory:
        CN=Person,CN=Schema,CN=Configuration,DC=tcgroup,DC=local
badPwdCount:
        0
mDBUseDefaults:
        TRUE
codePage:
        0
msExchDumpsterWarningQuota:
        20971520
mail:
        zhangliangliang@eetop.com
objectGUID:
        ???
msExchDumpsterQuota:
        31457280
msExchUserAccountControl:
        0
msExchModerationFlags:
        6
msExchMailboxSecurityDescriptor:
        ???
memberOf:
        CN=TCMC-DHIS,OU=邮件通讯组,DC=tcgroup,DC=local
        CN=HIS研发组,CN=Users,DC=tcgroup,DC=local
        CN=研发中心开发组,OU=邮件通讯组,DC=tcgroup,DC=local
        CN=TCMC,OU=邮件通讯组,DC=tcgroup,DC=local
        CN=TCCloud,OU=邮件通讯组,DC=tcgroup,DC=local
        CN=研发中心（通讯组）,OU=邮件通讯组,DC=tcgroup,DC=local
msExchMailboxGuid:
        ???
instanceType:
        4
internetEncoding:
        0
msExchPoliciesIncluded:
        f5409f3c-245b-4e8c-9388-48a78b5e7ab3
        {26491cfc-9e50-4857-861b-0cb8df22b5d7}
objectSid:
        ???
badPasswordTime:
        130382989540012012
proxyAddresses:
        SMTP:zhangliangliang@eetop.com
dSCorePropagationData:
        20130823103843.0Z
        20130823103334.0Z
        20130516064134.0Z
        16010101181633.0Z
objectClass:
        top
        person
        organizationalPerson
        user
msExchWhenMailboxCreated:
        20130218050850.0Z
name:
        张亮亮
msExchTransportRecipientSettingsFlags:
        0
garbageCollPeriod:
        1209600
msExchProvisioningFlags:
        0
userAccountControl:
        66048
primaryGroupID:
        513
lastLogon:
        130379382745825480
accountExpires:
        9223372036854775807
msExchBypassAudit:
        FALSE
lastLogoff:
        0
uSNChanged:
        46633016
lockoutTime:
        0
msExchRBACPolicyLink:
        CN=Default Role Assignment Policy,CN=Policies,CN=RBAC,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
cn:
        张亮亮
msExchArchiveWarnQuota:
        47185920
msExchVersion:
        44220983382016
msExchTextMessagingState:
        302120705
        16842751
title:
        JAVA研发工程师
protocolSettings:
        RemotePowerShell§1
logonCount:
        2111
msExchHomeServerName:
        /o=First Organization/ou=Exchange Administrative Group (FYDIBOHF23SPDLT)/cn=Configuration/cn=Servers/cn=EX01
mobile:
        18658853143
msExchMDBRulesQuota:
        64
homeMTA:
        CN=Microsoft MTA,CN=EX01,CN=Servers,CN=Exchange Administrative Group (FYDIBOHF23SPDLT),CN=Administrative Groups,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
sAMAccountType:
        805306368
msExchRecipientTypeDetails:
        1
legacyExchangeDN:
        /o=First Organization/ou=Exchange Administrative Group (FYDIBOHF23SPDLT)/cn=Recipients/cn=userd62c4768
uSNCreated:
        17118291
displayName:
        张亮亮
pwdLastSet:
        130301660907861222
userPrincipalName:
        zhangliangliang@eetop.com
msExchAddressBookFlags:
        1
whenChanged:
        20140303054241.0Z
lastLogonTimestamp:
        130382989610684995
msExchMailboxAuditEnable:
        FALSE
department:
        信息事业部
countryCode:
        0
mailNickname:
        zhangliangliang
msExchMailboxAuditLogAgeLimit:
        7776000
msExchArchiveQuota:
        52428800
distinguishedName:
        CN=张亮亮,OU=研发中心,OU=信息事业部,OU=通策集团,DC=tcgroup,DC=local
msExchRecipientDisplayType:
        1073741824
homeMDB:
        CN=First Mailbox DB,CN=Databases,CN=Exchange Administrative Group (FYDIBOHF23SPDLT),CN=Administrative Groups,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
msExchUMDtmfMap:
        reversedPhone:34135885681
        emailAddress:942645426454264
        lastNameFirstName:
        firstNameLastName:
msExchUMEnabledFlags2:
        -1
showInAddressBook:
        CN=Mailboxes(VLV),CN=All System Address Lists,CN=Address Lists Container,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
        CN=All Mailboxes(VLV),CN=All System Address Lists,CN=Address Lists Container,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
        CN=All Recipients(VLV),CN=All System Address Lists,CN=Address Lists Container,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
        CN=默认全局地址列表,CN=All Global Address Lists,CN=Address Lists Container,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
        CN=所有用户,CN=All Address Lists,CN=Address Lists Container,CN=First Organization,CN=Microsoft Exchange,CN=Services,CN=Configuration,DC=tcgroup,DC=local
msExchUserCulture:
        zh-CN
sAMAccountName:
        zhangliangliang

*/
