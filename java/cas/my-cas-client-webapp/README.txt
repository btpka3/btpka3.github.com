java.lang.RuntimeException: javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
  at org.jasig.cas.client.validation.Saml11TicketValidator.retrieveResponseFromServer(Saml11TicketValidator.java:203)

1. C:\Users\ZLL\Desktop>keytool -export -alias myJettyKey -file ./myJettyKey.cer -keystore ./jetty.jks -storepass 123456
保存在文件中的认证 <./myJettyKey.cer>

2. 备份 C:\Java\jre6\lib\security\cacerts

3. 导入
C:\Users\ZLL\Desktop>keytool -import -file ./myJettyKey.cer -keystore C:/Java/jre6/lib/security/cacerts -storepass changeit -noprompt
认证已添加至keystore中

http://magicmonster.com/kb/prg/java/ssl/pkix_path_building_failed.html

java.security.cert.CertificateException: No name matching localhost found
http://www.mkyong.com/webservices/jax-ws/java-security-cert-certificateexception-no-name-matching-localhost-found/
http://forum.springsource.org/showthread.php?47292-CAS-Authorization-Failure-No-Name-hostname-found!


https://wiki.jasig.org/display/CASC/Saml11TicketValidationFilter+Example



https://localhost:10001/login?TARGET=http%3A%2F%2Flocalhost%3A10010%2F
https://localhost:10001/login?TARGET=http%3A%2F%2Flocalhost%3A10010%2F
org.jasig.cas.client.validation.TicketValidationException: org.opensaml.SAMLException: 未能够识别出目标 'AAFSsPYAkNKN6Mb0Q6Li8D8gawrtLF8ittcJXiFRgZ6v8qAMA291liXs'票根