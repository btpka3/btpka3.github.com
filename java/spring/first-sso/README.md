

# 说明
该demo是为了学习Spring Security + CAS 实现单点登录。

* first-cas-server：是通过Maven War Overlay方式简单自定义的CAS中心。访问路径：
    `https://cas.localhost.me:8443/first-cas-server/`

* first-spring-cas：是使用CAS进行SSO的客户程序，这里使用Spring Security进行配置。访问路径：
    `http://app.localhost.me:8080/first-spring-cas/`

* first-spring-stateless：设计为尽量按照RESTFul提供JSON数据和业务操作的服务，用户不直接与其进行交互，但浏览器可以访问的到。
    `http://stateless.localhost.me:8080/first-spring-stateless/`

示例运行环境：  Windows OS + JDK 1.6 + STS 3.1 + Tomcat 6.x

# 配置步骤

## 为tomcat配置HTTPS

1. 生成自签名证书

    ```sh
    keytool -genkeypair \
            -alias mykey1 \
            -keyalg RSA \
            -keysize 1024 \
            -sigalg SHA1withRSA \
            -dname "CN=*.localhost.me, OU=R & D department, O=\"ABC Tech Co., Ltd\", L=Weihai, S=Shandong, C=CN" \
            -validity 365 \
            -keypass 123456 \
            -keystore tomcat.keystore \
            -storepass 123456
    ```

    注意：其中CN是域名
    注意：-keypass 和 -storepass tomcat貌似是要求一致的。

1. 修改 `${CATALINA_HOME}/conf/server.xml`

    ```xml
    <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
                   maxThreads="150" scheme="https" secure="true"
                   clientAuth="false" sslProtocol="TLS"
                   keystoreFile="D:/tomcat.keystore"
                   keystorePass="123456"/>
    ```

1. 为tomcat指定JVM启动参数
    * 若果在 Eclipse中启动Tomcat，则需要追加JVM参数：
        `-Djavax.net.ssl.trustStore="D:\tomcat.keystore" -Djavax.net.ssl.trustStorePassword="123456"`
    * 如果需要单独启动tomcat，则在 %CATALINA_HOME%/bin/catalina.bat 开头追加：
        `JAVA_OPTS = -Djavax.net.ssl.trustStore="D:\tomcat.keystore" -Djavax.net.ssl.trustStorePassword="123456"`

1. 修改 `C:\Windows\System32\drivers\etc\hosts` 或 `/etc/hosts`， 追加以下配置：

    ```text
    127.0.0.1       cas.localhost.me
    127.0.0.1       app.localhost.me
    127.0.0.1       stateless.localhost.me
    ```


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
自定义CAS服务器开发步骤

因为使用War Overlay的方式开发，所以需要先理解原有的代码结构。
先通过以下步骤获取 cas-server-webapp 3.5.1 的源代码

1. 通过 TortoiseGit 从 github 上 clone cas 3.5.x 分支。
    URL     - git://github.com/Jasig/cas.git
    Branch  - 3.5.x (分支列表这个可以在Github上看到)

2. 通过 TortoiseGit 切换到 3.5.1 这个 tag上
   本地cas代码仓库文件夹上右键 -> TortoiseGit -> Switch/Checkout...
   -> tags : 下拉选择 "v3.5.1"

3. 最后再STS中参照源代码，选择性的copy、复制要覆盖的文件到新的 war overlay项目中

注意：classpath:/messages_zh-CN.properties 中缺少
management.services.status.evaluationOrder.notupdated

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
学习总结：
1. 多个要单点登录、且同域的Stateful应用A、B，如果A已经登录，而B尚未登录，A的JS要调用B的服务
     /A/sample.html 中的JS调用 /B/isLogined 检查B是否已经登录。
     发现B尚未登录，则 /A/sample.html中的JS在当前dom中创建一个隐藏 iframe，地址是 /B/mustLogined,
     之后在 iframe 中由浏览器完成跳转到CAS，在跳回到 /B，完成 /B 的单点登录
     /A/sample.html 中的JS再次调用 /B/isLogined ，如果返回true，则可以调用其他的需要登录B才能调用的服务了。

2. 一个Stateful应用A，一个Stateless应用B均使用单点登录：
    如果B是后台服务，不对外公布，也不能通过浏览器访问，则A每次调用B的服务前（通过RMI或其他方式），
    需要通过PGT（在A登录时获取，每次使用后重新获取）先向 CAS 服务器获取 ProxyTicket
    （也即B的ServiceTicket），调用时将ServiceTicket一同传递，应用B要每次都向CAS服务器验证该ServiceTicket的有效性。

    如果B可以通过浏览器访问，与A同域，A要Ajax调用B的服务：
      A的Ajax要先从后台获取B的ServiceTicket，在通过HTTP GET/POST 调用B的服务。
      B返回的数据格式可以任意。

    如果B可以通过浏览器访问，与A不同域，A要Ajax调用B的服务：
      A的Ajax要先从后台获取B的ServiceTicket，再通过HTTP GET/POST 调用B的服务。
      而B返回的数据要使用JSONP格式，即结果是一个JavaScript回调函数包装，而不是 JSON 数据。


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Spring Web Flow 与 Spring MVC 集成
1. 定义一个SpringFlow的HandlerAdapter
<bean class="org.springframework.webflow.mvc.servlet.FlowHandlerAdapter">
  <property name="flowExecutor" ref="flowExecutor" />
</bean>

2. 定义Flow Mappings
将发送到 /hotels/booking 请求交由 id=hotels/booking 的flow执行
<bean class="org.springframework.webflow.mvc.servlet.FlowHandlerMapping">
  <property name="flowRegistry" ref="flowRegistry"/>
  <property name="order" value="0"/>
</bean>


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
RESTful API
https://wiki.jasig.org/display/CASUM/RESTful+API

* 在 maven的 setting.xml的激活的profile中追加以下仓库
<repository>
   <id>maven-restlet</id>
   <name>Public online Restlet repository</name>
   <url>http://maven.restlet.org</url>
</repository>

* 在 fisrt-cas-server 的 pom.xml中加入以下依赖
<dependency>
    <groupId>org.jasig.cas</groupId>
    <artifactId>cas-server-integration-restlet</artifactId>
    <version>${cas.version}</version>
    <type>jar</type>
</dependency>

* 在 fisrt-cas-server 的 pom.xml中加入 移除 cas-server-webapp 中的 cglib-full-2.0.2.jar
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.3</version>
        <configuration>
          <warName>first-cas-server</warName>
          <overlays>
            <overlay>
              <groupId>org.jasig.cas</groupId>
              <artifactId>cas-server-webapp</artifactId>
              <excludes>
                <exclude>WEB-INF/lib/cglib-full-*.jar</exclude>
              </excludes>
            </overlay>
          </overlays>
        </configuration>
      </plugin>
    </plugins>


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

未完成任务：
1. 自定义CAS服务器的皮肤
2. 自定义CAS服务器的持久化
3. CAS服务器与QQ、人人、新浪、谷歌等的OpenId联合
4. CAS服务器的集群
5. 使用CAS的RemeberMe？且发现RememberMe AccessDenied 之后能够自动重新登录？
    Spring Security目前尚未实现
    https://wiki.jasig.org/display/CASUM/Remember+Me
    https://groups.google.com/forum/#!msg/jasig-cas-user/w9f5LAyHsWA/McpQQOcRUzgJ
    https://jira.springsource.org/browse/SEC-1986
7. OAuth?

AuthenticationTrustResolver#isAnonymous()
使用Properties方式时的调用路径
  at AuthenticatedVoter#vote()
  at AffirmativeBased#decide()
  at MethodInvocationPrivilegeEvaluator#isAllowed() / DefaultWebInvocationPrivilegeEvaluator#isAllowed()
使用Expression时的调用路径
  at SecurityExpressionRoot#isAnonymous()



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
TGT 是存放于 SSO 服务器的域名下的Cookie里的。

org.jasig.cas.authentication.principal.Credentials
  Marker Interface。需要客户端提供，以便验证的东西（用户名，密码，证书等）。
  验证通过后会转换成 Principal。
  org.jasig.cas.authentication.principal.HttpBasedServiceCredentials
    验证ST时，提供的验证成功时发送PGT的URL。

org.jasig.cas.authentication.Authentication
  + Principal
  + 属性Map
  + 认证时间戳

  代表一个验证成功的认证请求，还包含一些其他的额外信息（比如认证时间等）。


org.jasig.cas.authentication.principal.Principal
  认证后的某种东西，比如人或者服务。有ID和属性列表组成。


org.jasig.cas.validation.Assertion
  调用 CentralAuthenticationService.validateServiceTicket(String, Service) 的返回值。
  主要有一组 Authentication、ST要验证的 Service、 和 isFromNewLogin 构成。

org.jasig.cas.authentication.principal.UsernamePasswordCredentials



org.jasig.cas.authentication.AuthenticationManager
  org.jasig.cas.authentication.handler.AuthenticationHandler
  org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver
    (org.jasig.cas.authentication.principal.UsernamePasswordCredentialsToPrincipalResolver)
    org.jasig.cas.authentication.principal.SimplePrincipal

org.jasig.cas.CentralAuthenticationService
    createTicketGrantingTicket(Credentials)
    delegateTicketGrantingTicket(String, Credentials)
    destroyTicketGrantingTicket(String)
    grantServiceTicket(String, Service)
    grantServiceTicket(String, Service, Credentials)
    validateServiceTicket(String, Service)

org.jasig.cas.CentralAuthenticationServiceImpl
  + org.jasig.cas.ticket.registry.TicketRegistry
  + org.jasig.cas.authentication.AuthenticationManager
      负责 org.jasig.cas.authentication.principal.Credentials -> org.jasig.cas.authentication.Authentication
  + org.jasig.cas.util.UniqueTicketIdGenerator
  + org.jasig.cas.ticket.ExpirationPolicy
  + org.jasig.cas.services.ServicesManager

  + org.jasig.cas.authentication.principal.PersistentIdGenerator



String serviceTicketId
  ST-xxxxx

org.jasig.cas.ticket.ServiceTicket
    包含 org.jasig.cas.authentication.principal.Service
            包含 org.jasig.cas.authentication.principal.Principal

org.jasig.cas.ticket.registry.TicketRegistry


