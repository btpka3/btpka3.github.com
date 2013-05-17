
可自执行的war：以下功能测试可用：
1. 通过注解自定义Servlet
2. 通过注解自定义Filter
3. 访问静态资源
4. 访问JSP
5. 使用EL表达式
6. 使用JSTL





http://stackoverflow.com/questions/6020495/embedded-jetty-fails-to-load-jsp-taglibs-when-classpath-specified-in-jar


==================================================================================
问题记录：使用JSTL，总是报错：
The absolute uri: http://java.sun.com/jsp/jstl/core cannot be resolved in either web.xml or the jar files deployed with this application

源代码参考：
org.eclipse.jetty.webapp.StandardDescriptorProcessor
org.apache.jasper.runtime.TldScanner#scanJar(JarURLConnection, List<String>, boolean)

远程Debug：
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -jar first-exec-war-0.0.1-SNAPSHOT.war

追踪分析结论如下：

TldScanner 按照以下顺序搜索tld文件
1. 先查找 /WEB-INF/web.xml 中定义的
    不能包含URL是以下路径的标签库（参考mapTldLocation()）
      http://java.sun.com/jsp/jstl/core
      (使用JSF时)http://java.sun.com/jsf/core
      (使用JSF时)http://java.sun.com/jsf/html

2. 再查找 /WEB-INF/lib/*.jar!META-INF/**/*.tld, 并且：
    不能包含URL是以下路径的标签库（参考mapTldLocation()）
      http://java.sun.com/jsp/jstl/core
      (使用JSF时)http://java.sun.com/jsf/core
      (使用JSF时)http://java.sun.com/jsf/html

ClassLoader层次：
sun.misc.Launcher$ExtClassLoader
              [
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/access-bridge-64.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/dnsns.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/jaccess.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/localedata.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/sunec.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/sunjce_provider.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/sunmscapi.jar,
                file:/C:/Java/jdk1.7.0_21/jre/lib/ext/zipfs.jar
              ]
  sun.misc.Launcher$AppClassLoader
              [
                file:/D:/zll/git/github/btpka3.github.com/java/jetty/first-exec-war/target/first-exec-war-0.0.1-SNAPSHOT.war
              ]
      WebAppClassLoader
              [
                file:/D:/zll/git/github/btpka3.github.com/java/jetty/first-exec-war/target/first-exec-war-0.0.1-SNAPSHOT/WEB-INF/classes/,
                file:/D:/zll/git/github/btpka3.github.com/java/jetty/first-exec-war/target/first-exec-war-0.0.1-SNAPSHOT/WEB-INF/lib/commons-lang3-3.0.1.jar,
                file:/D:/zll/git/github/btpka3.github.com/java/jetty/first-exec-war/target/first-exec-war-0.0.1-SNAPSHOT/WEB-INF/lib/first-exec-war-0.0.1-SNAPSHOT.jar,
                file:/D:/zll/git/github/btpka3.github.com/java/jetty/first-exec-war/target/first-exec-war-0.0.1-SNAPSHOT/WEB-INF/lib/joda-time-2.2.jar
              ]

3. 最后查找 /WEB-INF/**/*.tld
    如果有/WEB-INF/tags/这个目录，则其中的各个目录中的tld文件名只能以 "implicit.tld" 结尾

解决方法：不要以war结尾，而是以jar结尾。



