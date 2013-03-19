Step 1: 在STS中 File -> New -> Maven Project，然后 创建一个新的简单的工程。
Step 2: 修改 POM.xml
  - 加入对 cas-server-webapp 的依赖，由于它依赖一个 1.1b 版的opensaml, 所以排除该依赖。
    - 加入对 opensaml 另外一个替代版本的依赖。我使用的是2.5的，但是它依赖于 xerces:xml-apis，因为名称不规范而找不到，就排除吧。
    - 加入对xml-apis:xml-apis:jar:1.4.01的依赖
  - 加入对 cas-server-support-jdbc 的依赖。 
  - 为了能有相应的Servlet服务器运行，添加对jetty-maven-plugin的依赖 【provided】

参考：
https://svn.middleware.vt.edu/svn/middleware/cas/cas-server/trunk/
https://wiki.jasig.org/display/CASUM/Best+Practice+-+Setting+Up+CAS+Locally+using+the+Maven2+WAR+Overlay+Method
https://wiki.jasig.org/display/CASUM/Using+JDBC+for+Authentication
https://wiki.jasig.org/display/CAS/Examples+to+Configure+CAS
https://wiki.jasig.org/display/CASUM/JDBC
http://maven.apache.org/plugins/maven-war-plugin/overlays.html

cas-server-core API 
http://developer.jasig.org/projects/cas/cas-server-core/cas-server/cas-server-core/apidocs/

cas-server-support-jdbc API
http://developer.jasig.org/projects/cas/cas-server-support-jdbc/cas-server/cas-server-support-jdbc/apidocs/

org.jasig.cas.CentralAuthenticationService

org.jasig.cas.authentication.Authentication
  - 代表一个成功认证的请求。它包含Principal以及其他额外信息（如认证时间，附加属性等）
org.jasig.cas.authentication.AuthenticationManager
  - 负责判定给定的credential是否真实有效。
org.jasig.cas.authentication.AuthenticationMetaDataPopulator
  - 是认证流程的一个扩展点。它允许CAS为所有的认证针对给定的认证请求和Pricipal提供额外的属性信息


org.jasig.cas.authentication.principal.Credentials
  - 没有任何约束的标记接口，已知有以下类型：
  - HttpBasedServiceCredentials: 其内容仅仅是一个回调URL
  - UsernamePasswordCredentials: 其内容为用户名、密码
  - RememberMeUsernamePasswordCredentials: 在前者的基础上追加了一个是否RememberMe的标志
org.jasig.cas.authentication.principal.Credentials.AuthenticationHandler
  - 检查是否支持给定的credentials，如果支持，则提供对其进行验证的方法。
org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver
  - 检查是否支持给定的credentials，如果支持，则根据其获取相应的principal
org.jasig.cas.authentication.AuthenticationMetaDataPopulator
org.jasig.cas.authentication.handler.NamedAuthenticationHandler
org.jasig.cas.authentication.handler.PasswordEncoder

org.jasig.cas.authentication.principal.RememberMeCredentials
org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver
org.jasig.cas.authentication.principal.Principal
  - 是对认证后的事物的一个宽泛抽象，已知有以下类型：
  - GoogleAccountsService: Google账户服务
  - SamlService: 代表一个要使用SAML的服务
  - SimplePrincipal：内容为一个必选的ID属性，和其他可选的不可修改的属性Map。
  - SimpleWebApplicationServiceImpl: 代表一个需要使用CAS协议的服务
org.jasig.cas.authentication.principal.Service
  - 没有任何约束的标记接口，代表一个要使用CAS的服务。
org.jasig.cas.authentication.principal.WebApplicationService
  - 代表一个要使用CAS的Web服务。

org.jasig.cas.services.RegisteredService
  - 能够被ServicesManager进行管理的服务接口
org.jasig.cas.services.ServiceRegistryDao
  - 负责所有注册服务的保存、查找和删除工作。已知实现：
  - InMemoryServiceRegistryDaoImpl: 在内存中保存，用户测试和示例
  - JpaServiceRegistryDaoImpl: 使用JPA将注册的服务保存到数据库中
org.jasig.cas.services.ServicesManager
  - 对已经在CAS注册且希望使用CAS的服务的存储、恢复、匹配进行管理
org.jasig.cas.services.ReloadableServicesManager
  - 扩展自ServicesManager，并允许对管理的Service重新加载，已知实现：
  - DefaultServicesManagerImpl: 如果没有一个服务注册到该管理器，则认为该ServiceManager被禁用，且允许所有的服务使用CAS

org.jasig.cas.ticket.proxy.ProxyHandler
  - 在处理代理时需要做处理的一个抽象。很实用，因为一般来说所有认证流程都很相似，所需要做的仅仅是切换一下该接口的实现。

org.jasig.cas.ticket.registry.support.LockingStrategy
  - 以策略模式定义加锁策略，使之可以支持排除某些处理。已知实现：
  - JdbcLockingStrategy: 有以下特性时可以将加锁状态保存在数据库中：
  -     排他性: 只有一个客户端能够获取一个锁
  -     不可再入: 如果已经获取了该锁，再次尝试获取该锁时将会失败
  -     锁有时效性: 到了指定的过期时间之后，即便原有锁得持有者未释放该锁，其他请求者也可以获取该锁。
  -   参考以下模板：
  -   CREATE TABLE LOCKS (
  -     APPLICATION_ID VARCHAR(50) NOT NULL,
  -     UNIQUE_ID VARCHAR(50) NULL,
  -     EXPIRATION_DATE TIMESTAMP NULL,
  -     PRIMARY KEY (APPLICATION_ID)
  -   );
  - NoOpLockingStrategy: 什么都没做
org.jasig.cas.ticket.registry.RegistryCleaner
  - 策略模式接口，用以清理ticket注册表。已知实现：
  - DefaultTicketRegistryCleaner: 将扫描CAS中所有的ticket注册表，并删除过期的ticket。
  -   CAS的功能并不依赖于这些因过期而删除的ticket。
  -   3.3.6版本之后可以使用加锁策略以支持高可用的CAS集群模式，此时，在清理ticket注册表的时候，只允许其中一个CAS服务节点执行清理操作，
  -   这样可以防止死锁
org.jasig.cas.ticket.registry.TicketRegistry

org.jasig.cas.ticket.ExpirationPolicy
  - 判断Ticket是否过期的抽象接口。已知实现：
  - HardTimeoutExpirationPolicy: 从创建之后有个固定时间点的有效期
  - MultiTimeUseOrTimeoutExpirationPolicy: 基于使用次数或过期时间的过期策略
  - NeverExpiresExpirationPolicy: 从不过期。
  - RememberMeDelegatingExpirationPolicy: 如果支持RememberMe，则使用设定的
  -   RememberMe过期策略进行判断，否则，使用给定的Session过期策略进行判断。
  - ThrottledUseAndTimeoutExpirationPolicy: 限定使用频率和过期时间的过期策略。
  - TimeoutExpirationPolicy: 指定过期时间的过期策略。
org.jasig.cas.ticket.Ticket
  - Ticket的抽象概念。
org.jasig.cas.ticket.ServiceTicket
  - 用户访问某个Service，一般是一次性的Ticket。已知实现类：
  - ServiceTicketImpl
org.jasig.cas.ticket.TicketGrantingTicket
  - 获取Ticket的Ticket。是访问CAS服务的主要通道。已知实现类：
  - TicketGrantingTicketImpl
org.jasig.cas.ticket.TicketState
  - Ticket的状态接口。提供以下信息： Authentication、使用次数、创建时间、最后使用时间。已知实现类：
  - ServiceTicketImpl
  -  TicketGrantingTicketImpl

org.jasig.cas.validation.Assertion
  - 包含一个Principal链条。第一个是用户的登录的Principal，其他的是代理Principal。已知实现类：
  - ImmutableAssertionImpl
org.jasig.cas.validation.ValidationSpecification
  - 用于在检查时强加一些约束和要求（比如： renew=true）。已知实现类：
  - Cas10ProtocolValidationSpecification
  - Cas20ProtocolValidationSpecification
  - Cas20WithoutProxyingValidationSpecification

  
org.jasig.cas.web.support.ArgumentExtractor
  - 用于从请求中获取Service和Ticket。已知实现类：
  - CasArgumentExtractor
  - GoogleAccountsArgumentExtractor
  - SamlArgumentExtractor



-------
credentials
org.jasig.cas.web.flow.AbstractNonInteractiveCredentialsAction
  protected abstract Credentials constructCredentialsFromRequest(final RequestContext context);
  
org.jasig.cas.web.ServiceValidateController
  # 使用pgtUrl参数构造一个HttpBasedServiceCredentials 
  protected Credentials getServiceCredentialsFromRequest(final HttpServletRequest request)
  
CentralAuthenticationService




Maven Jetty Plugin
http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin
http://jdonee.iteye.com/blog/290789
http://docs.codehaus.org/display/JETTY/Debugging+with+the+Maven+Jetty+Plugin+inside+Eclipse


keytool -genkeypair -alias myJettyKey -keyalg RSA -keysize 1024 -sigalg SHA1withRSA -dname "CN=localhost, OU=No.1 Department, O=ABCSystems.Inc, L=Weihai, S=Shandong, C=ZH" -validity 365 -keypass 123456 -keystore jetty.jks -storepass 123456

如何调试？——使用Jetty Maven Plugin
1 以debug模式启动该插件。
1.1 在pom.xml中配置好该插件以后，在命令行中输入以下命令：
CMD\> set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=10008,server=y,suspend=n
CMD\> cd %YOUR_PROJECT_HOME%
CMD\> mvn jetty:run
1.2 或者在Eclipse如下配置并启动：
  > Run 菜单 -> External Tools -> External Tools Configurations...
    - 弹出窗口:
      - 左侧：双击"Program"新建一个 
      - 右侧：
        - Main标签页:
          - 在"Location" 中输入 mvn.bat 文件所在的路径
          - 在"Working Directory" 中通过点击 "Broswse Workspace"按钮选择到你要调试的工程
          - 在"Arguments"中输入"jetty:run"
        - Enviroment标签页: 新建以下环境变量：
          - 变量名: MAVEN_OPTS
          - 变量值: -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n
          -   （注意：上述suspend如果为y，则表示只有当外部调试控制程序——这里指eclipse连接到该调试端口时，才开始启动调戏。）
          - 如果后续执行报该错误：NoClassDefFoundError: org/codehaus/classworlds/Launcher，则还要追加以下变量:
          - 变量名: M2_HOME
          - 变量值：Maven的安装目录   
      - 最后启动。（之后就可以先把该配置项添加到Favorites中，通过"External Tools"图标快速选择并运行了）
  > Run 菜单 -> Debug Configurations...
    - 弹出窗口:
      - 左侧：双击"Program"新建一个"Remote Java Application"
      - 右侧：
        - Connect标签页
          - 在"Name"中输入你自定的名称
          - 在"Project"中: 通过点击"Browse..."按钮选择你要调试的工程
          - Connect Type 保持默认——即：Standard(Socket Attach)
          - 在Connection Properties 中，
            - Host选择为你运行测试程序的服务器的地址，我这里为localhost；
            - Port为上一步中MAVEN_OPTS变量中address指定的端口号，这里为4000
          - 点击"Debug"按钮开始调试
          
CMD\> netstat -a -n -o | findstr 10008
  TCP    0.0.0.0:10008          0.0.0.0:0              LISTENING       4428
CMD\> taskkill /F /PID 4428


C:\Users\ZLL>netstat -a -n -o | findstr 10018
  TCP    0.0.0.0:10018          0.0.0.0:0              LISTENING       6048
  TCP    127.0.0.1:56000        127.0.0.1:10018        TIME_WAIT       0

C:\Users\ZLL>netstat -a -n -o | findstr 10018
  TCP    0.0.0.0:10018          0.0.0.0:0              LISTENING       6048

  
FilterConfig cannot be resolved It is indirectly referenced from required .class files
-> 将 jetty-maven-plugin 作为 provided dependency 添加依赖即可。