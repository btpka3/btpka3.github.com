https://jaxb.java.net/guide/Using_different_datatypes.html
http://docs.oracle.com/cd/E13222_01/wls/docs103/webserv/data_types.html
http://stackoverflow.com/questions/2364110/whats-the-justification-behind-disallowing-partial-put
http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html


说明：
该示例主要是受到Dojo的JsonRest的启发，加之对dojo、RESTFul Web Service、Spring 的兴趣而实现 JsonRest 的服务器端业务逻辑。
为了简单起见，该示例没有使用数据库，故查询、排序、分页均是在程序中进行的。

序列化和反序列化:

为了简化开发，一般都先定义RESTFul Web服务通信规范，也即 Contract First。具体而言就是：
1. 先定义 xsd 文件，描述通信的JSON/XML的内容
2. 通过jaxb2-maven-plugin 插件自动生成相应的Java类，并在Controller中接受、返回该类型对象。
3. 通过Spring的相应的 HttpMessageConverter 自动完成序列化和反序列化。
 Spring 3.1 之后默认使用：
  org.springframework.core.convert.support.DefaultConversionService
  它注册了很多默认的converter，比如
  org.springframework.core.convert.support.ObjectToObjectConverter
该Converter是非public的，因此只能通过源代码看到说明。
它依次尝试使用目标类型的静态 valueOf(srcType)， 接收单个srcType的构造函数进行转换。
这样的话，就可以不必再些专门的Converter类了。



关于缓存：
1. 只对 GET、HEAD有效
2. POST、PUT、DELETE应当使cache失效
3. 200, 203, 206, 300, 301 or 410的响应可以被缓存
但是如果缓存系统不支持 Range、Content-Range头的话，就不应缓存 206 (Partial Content)
4. Last-Modified 在业务逻辑执行前判断，而 ETag则在执行后判断，后者并不能降低CPU使用率。两者均可降低带宽使用。


：




TODO:
  JSONP?
  XML?

文件上传，如果需要使用不同的最大大小，最好使用不同的Spring DispatcherServlet， 进而使用不同的 multipartResolver

PUT是一个完整的实体对象，不能被部分更新。
如果需要部分更新，需要使用PATCH方法。


注意：
1. 要先运行  mvn clean process-classes 之后，
2. 整个工程 F5 刷新
3. Server: Tomcat -> Add and Remove, 选择 first-spring-rs 工程, 最后一定要 点击 “Publish to the Server”
4. 启动或调试 tomcat。
5. 移除时，同第三步，但同样要注意最后一定要 “Publish”



xjc -p me.test.first.spring.rs.model Class.xsd






Problem :
1. xjc encoding.

Maven中的goal、phase、build lifecycle都啥概念？
命令mvn个格式为：
mvn [options] [<goal(s)>] [<phase(s)>]
archetype:generate


《Introduction to the Build Lifecycle》
http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.htm
■有3个内建的Lifecycle：clean, default, site。
■每一个lifecycle会执行一系列的phase。
且如果工程下有多个子工程，则命令"mvn clean install"将会先执行每个工程的clean，
然后执行每个工程的install（包含位于install之前的validate，compile等）
■一个Phase由Goal组成。
一个Goal代表一个特定的task，它可以绑定到一个或多个phase上。
则该goal会在相应的phase执行的时候被调用。
注意：phase和goal的关系式多对多的关系，因此，一个phase也可以包含多个goal。
从Maven 2.0.5以后， 这些goal的执行顺序则按照pom.xml中出现的顺序执行，但是不能够同一个plugin多次实例化/设定。
而从Maven 2.0.11以后，就可以将同一个plugin多次实例化/设定，他们会被分组并顺序执行。
但是如果一个phase没有一个goal绑定到它，则它不会执行。

假如一个Goal没有绑定到
任何一个phase，则它可以在在构建的生命周期之外通过调用单独执行。
goal或phase的执行顺序则按命令行参数指定的顺执行。
比如: mvn clean dependency:copy-dependencies package
会优先执行phase "clean"，然后是goal "dependency:copy-dependencies"，最后是phase "package".

clean: 负责工程的清理
  pre-clean
  clean
  post-clean

default: 处理工程的部署
  validate
  initialize
  generate-sources
  process-sources
  generate-resources
  process-resources
  compile
  process-classes
  generate-test-sources
  process-test-sources
  generate-test-resources
  process-test-resources
  test-compile
  process-test-classes
  test
  prepare-package
  package
  pre-integration-test
  integration-test
  post-integration-test
  verify
  install
  deploy

site: 负责工程站点上的文档创建工作
  pre-site
  site
  post-site
  site-deploy



http://svn.apache.org/repos/asf/maven/maven-2/tags/maven-2.2.0/maven-core/src/main/resources/META-INF/plexus/components.xml
http://svn.apache.org/repos/asf/maven/maven-3/branches/MNG-3004/maven-core/src/main/resources/META-INF/plexus/components.xml





http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html


Q: HTTP POST vs. PUT
http://www.elharo.com/blog/software-development/web-development/2005/12/08/post-vs-put/

HTTP PUT 是指Client向服务器端发送了一个文章，并告诉服务器“我就要把这个文章放到这个URL上，将来我就在这个URL上查看这个文章”。
(HTTP 200 OK)
(HTTP 201 CREATED)
(HTTP 204 NO CONTENT)
HTTP POST 是指Client向服务器公布的一个服务入口URL发送了一篇文章，但服务器可能会有以下处理：
   1. 即时处理：
      - 发送的URL是服务器对所有Client的一个统一入口（或可以理解为一个目录）：
      1.1 根据XXXX条例判断您的类容为一级保密内容，我已经正确处理，但不向你返回结果(HTTP 204 NO CONTENT)
      1.2 您的文章已经接受并创建，请于到以下位置（由HTTP Header的Location指明）进行访问。(HTTP 201 CREATED)

      - 发送的URL是某个该文章的前一版本的URL
      1.3 您的文章内容更新成功。(HTTP 205 RESET CONTENT)

   2. 异步处理：
      2.1 您好，你的文章已经接收，我们会在24小时内完成处理。(HTTP 202 ACCEPTED)
